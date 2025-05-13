package se.samuel.minimums.Recipe;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Ingredients.IngredientsMapper;
import se.samuel.minimums.Ingredients.Ingredients;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RecipesService {

    private final RecipesRepo recipesRepo;
    private final RecipesMapper recipesMapper;
    private final IngredientsMapper ingredientsMapper;

    public List<RecipesDto> getAllRecipes() {
        return recipesRepo.findAll()
                .stream()
                .map(recipesMapper::toDto)
                .toList();
    }

    public Optional<RecipesDto> getRecipeById(Long id) {
        return recipesRepo.findById(id)
                .map(recipesMapper::toDto);
    }
    public RecipesDto createRecipe(RecipesDto recipesDto) {

        Recipes newRecipe = recipesMapper.toEntity(recipesDto);
        if (newRecipe.getIngredients() != null) {
            newRecipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(newRecipe));
        }
        Recipes savedRecipe = recipesRepo.save(newRecipe);
        return recipesMapper.toDto(savedRecipe);
    }

    public RecipesDto updateRecipe(Long id, RecipesDto recipesDto) {
        Recipes existing = recipesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        existing.setName(recipesDto.getName());
        existing.setDescription(recipesDto.getDescription());
        existing.setInstructions(recipesDto.getInstructions());
        existing.setFromAge(recipesDto.getFromAge());
        existing.setToAge(recipesDto.getToAge());

        // UPDATE INGREDIENTS
        if (recipesDto.getIngredientsDtoList() != null) {
            existing.getIngredients().clear();
            List<Ingredients> newIngredients = recipesDto.getIngredientsDtoList().stream()
                    .map(ingredientsMapper::toEntity)
                    .peek(ingredient -> ingredient.setRecipe(existing))
                    .toList();
            existing.getIngredients().addAll(newIngredients);
        }
        Recipes updated = recipesRepo.save(existing);
        return recipesMapper.toDto(updated);
    }

    public void deleteRecipe(Long id) {
        if (!recipesRepo.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipesRepo.deleteById(id);
    }
}
