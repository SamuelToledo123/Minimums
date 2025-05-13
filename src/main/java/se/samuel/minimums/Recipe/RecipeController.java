package se.samuel.minimums.Recipe;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Recipe.RecipesDto;
import se.samuel.minimums.Recipe.RecipesService;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipesService recipesService;
    @GetMapping
    public ResponseEntity<List<RecipesDto>> getAllRecipes() {
        List<RecipesDto> recipes = recipesService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipesDto> getRecipeById(@PathVariable Long id) {
        return recipesService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<RecipesDto> createRecipe(@RequestBody RecipesDto recipeDto) {
        RecipesDto createdRecipe = recipesService.createRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RecipesDto> updateRecipe(@PathVariable Long id, @RequestBody RecipesDto recipeDto) {
        try {
            RecipesDto updated = recipesService.updateRecipe(id, recipeDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        try {
            recipesService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}