package se.samuel.minimums.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.MealPlanDto;
import se.samuel.minimums.Service.MealPlanService;

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
    public ResponseEntity<MealPlanDto> create(@Valid @RequestBody MealPlanDto mealPlanDto) {
        MealPlanDto created = mealPlanService.createMealPlan(mealPlanDto);
        return ResponseEntity.status(201).body(created);
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
}
