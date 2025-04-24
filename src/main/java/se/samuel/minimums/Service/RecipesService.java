package se.samuel.minimums.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Converter.RecipesMapper;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.RecipesRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipesService {

    @Autowired
    private RecipesRepo recipesRepo;

    @Autowired
    private RecipesMapper recipesMapper;

    @Autowired
    IngredientsMapper ingredientsMapper;

    public List<RecipesDto> getAllRecipes() {
        return recipesRepo.findAll()
                .stream()
                .map(recipesMapper::RecipesToRecipesDto)
                .toList();
    }

    public Optional<RecipesDto> getRecipeById(Long id) {
        return recipesRepo.findById(id)
                .map(recipesMapper::RecipesToRecipesDto);
    }
    public RecipesDto createRecipe(RecipesDto recipesDto) {

        Recipes newRecipe = recipesMapper.RecipesDtoToRecipes(recipesDto);
        if (newRecipe.getIngredients() != null) {
            newRecipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(newRecipe));
        }
        Recipes savedRecipe = recipesRepo.save(newRecipe);
        return recipesMapper.RecipesToRecipesDto(savedRecipe);
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
                    .map(ingredientsMapper::IngredientsDtoToIngredients)
                    .peek(ingredient -> ingredient.setRecipe(existing))
                    .toList();
            existing.getIngredients().addAll(newIngredients);
        }
        Recipes updated = recipesRepo.save(existing);
        return recipesMapper.RecipesToRecipesDto(updated);
    }

    public void deleteRecipe(Long id) {
        if (!recipesRepo.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipesRepo.deleteById(id);
    }
}
