package se.samuel.minimums.MealPlan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.Recipe.Recipes;
import se.samuel.minimums.AppUser.AppUserRepo;
import se.samuel.minimums.Recipe.RecipesRepo;

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

        mealPlanRepo.delete(mealPlan);

        return "MealPlan deleted successfully.";
    }
}
