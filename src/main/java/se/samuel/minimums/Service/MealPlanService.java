package se.samuel.minimums.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.samuel.minimums.Converter.MealPlanMapper;
import se.samuel.minimums.Dto.MealPlanDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Models.Child;
import se.samuel.minimums.Models.MealPlan;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.AppUserRepo;
import se.samuel.minimums.Repo.MealPlanRepo;
import se.samuel.minimums.Repo.RecipesRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealPlanService {

    private final MealPlanRepo mealPlanRepo;
    private final MealPlanMapper mealPlanMapper;

    private final AppUserRepo appUserRepo;
    private final RecipesRepo recipesRepo;


    public List<MealPlanDto> getAllMealPlans() {
        return mealPlanRepo.findAll().stream()
                .map(mealPlanMapper::toDto).toList();
    }

    public Optional<MealPlanDto> findMealPlanById(Long id) {
        return mealPlanRepo.findById(id)
                .map(mealPlanMapper::toDto);
    }

    public MealPlanDto createMealPlan(MealPlanDto mealPlanDto, Authentication auth) {
        String email = auth.getName();

        AppUser appUser = appUserRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User with email: " + email + " was not found"));

        List<Recipes> recipes = Optional.ofNullable(mealPlanDto.getRecipes())
                .orElse(List.of())
                .stream()
                .map(dto -> recipesRepo.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Recipe not found: " + dto.getId())))
                .toList();

        MealPlan mealPlan = mealPlanMapper.toEntity(mealPlanDto);
        mealPlan.setUser(appUser);
        mealPlan.setRecipes(recipes);

        MealPlan saved = mealPlanRepo.save(mealPlan);
        return mealPlanMapper.toDto(saved);
    }

    public MealPlanDto updateMealPlan(Long id, MealPlanDto mealPlanDto) {
        MealPlan existingMealPlan = mealPlanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MealPlan not found with id: " + id));

        existingMealPlan.setMealType(mealPlanDto.getMealType());
        existingMealPlan.setDate(mealPlanDto.getDate());

        List<Recipes> updatedRecipes = Optional.ofNullable(mealPlanDto.getRecipes())
                .orElse(Collections.emptyList())
                .stream()
                .map(dto -> recipesRepo.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Recipe not found: " + dto.getId())))
                .toList();

        existingMealPlan.setRecipes(new ArrayList<>(updatedRecipes));

        MealPlan updatedMealPlan = mealPlanRepo.save(existingMealPlan);
        return mealPlanMapper.toDto(updatedMealPlan);
    }

    public String deleteMealPlan(Long id) {
        MealPlan mealPlan = mealPlanRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MealPlan not found"));

        AppUser user = mealPlan.getUser();
        user.getMealPlans().remove(mealPlan);

        for (Recipes recipe : mealPlan.getRecipes()) {
            recipe.setMealPlan(null);
        }

        mealPlanRepo.delete(mealPlan);

        return "MealPlan deleted successfully.";
    }

    public MealPlanDto addRecipeToMealPlan(Long mealPlanId, Long recipeId) {
        MealPlan mealPlan = mealPlanRepo.findById(mealPlanId)
                .orElseThrow(() -> new RuntimeException("Meal plan not found with id: " + mealPlanId));

        Recipes recipe = recipesRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));
        if (mealPlan.getRecipes() != null && mealPlan.getRecipes().contains(recipe)) {
            throw new RuntimeException("Recipe already added to this meal plan.");
        }
        recipe.setMealPlan(mealPlan);
        if (mealPlan.getRecipes() == null) {
            mealPlan.setRecipes(new ArrayList<>());
        }
        mealPlan.getRecipes().add(recipe);
        mealPlanRepo.save(mealPlan);
        return mealPlanMapper.toDto(mealPlan);
    }
}
