package se.samuel.minimums.MealPlan;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @GetMapping
    public ResponseEntity<List<MealPlanDto>> getAll() {
        return ResponseEntity.ok(mealPlanService.getAllMealPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlanDto> getById(@PathVariable Long id) {
        return mealPlanService.findMealPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MealPlanDto> create(@RequestBody MealPlanDto mealPlanDto, Authentication auth) {
        MealPlanDto created = mealPlanService.createMealPlan(mealPlanDto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlanDto> update(@PathVariable Long id, @Valid @RequestBody MealPlanDto mealPlanDto) {
        MealPlanDto updated = mealPlanService.updateMealPlan(id, mealPlanDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String result = mealPlanService.deleteMealPlan(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{mealPlanId}/recipes/{recipeId}")
    public ResponseEntity<MealPlanDto> addRecipeToMealPlan(
            @PathVariable Long mealPlanId,
            @PathVariable Long recipeId
    ) {
        return ResponseEntity.ok(mealPlanService.addRecipeToMealPlan(mealPlanId, recipeId));
    }


}
