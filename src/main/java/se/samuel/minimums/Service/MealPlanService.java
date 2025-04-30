package se.samuel.minimums.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.MealPlanMapper;
import se.samuel.minimums.Dto.MealPlanDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Models.MealPlan;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.AppUserRepo;
import se.samuel.minimums.Repo.MealPlanRepo;
import se.samuel.minimums.Repo.RecipesRepo;

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

    public MealPlanDto createMealPlan(MealPlanDto mealPlanDto) {

        AppUser appUser = appUserRepo.findByEmail(mealPlanDto.getAppUser().getEmail()).orElseThrow(() ->
                new RuntimeException("User with email: " + mealPlanDto.getAppUser().getEmail() + " was not found"));

        List<Recipes> recipes = mealPlanDto.getRecipes().stream()
                .map(recipe -> recipesRepo.findById(recipe.getId())
                        .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipe.getId())))
                .toList();

        MealPlan mealPlan = mealPlanMapper.toEntity(mealPlanDto);

        mealPlan.setUser(appUser);
        mealPlan.setRecipes(recipes);

        MealPlan saved = mealPlanRepo.save(mealPlan);

        return mealPlanMapper.toDto(saved);
    }

    public MealPlanDto updateMealPlan(Long id, MealPlanDto mealPlanDto) {
        MealPlan existingMealPlan = mealPlanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        existingMealPlan.setMealType(mealPlanDto.getMealType());
        existingMealPlan.setDate(mealPlanDto.getDate());

        appUserRepo.findByEmail(mealPlanDto.getAppUser().getEmail())
                .ifPresent(existingMealPlan::setUser);

        List<Recipes> updatedRecipes = mealPlanDto.getRecipes().stream()
                .map(dto -> recipesRepo.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Recipe not found: " + dto.getId())))
                .toList();

        existingMealPlan.setRecipes(updatedRecipes);

        MealPlan updatedMealPlan = mealPlanRepo.save(existingMealPlan);
        return mealPlanMapper.toDto(updatedMealPlan);
    }

    public String deleteMealPlan(Long id) {
        MealPlan mealPlan = mealPlanRepo.findById(id).orElseThrow(() -> new RuntimeException("Meal plan was not found"));
        mealPlanRepo.delete(mealPlan);

        return "Meal plan was deleted";
    }
}
