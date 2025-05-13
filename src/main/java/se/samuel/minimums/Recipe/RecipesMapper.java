package se.samuel.minimums.Recipe;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Ingredients.Ingredients;
import se.samuel.minimums.Ingredients.IngredientsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipesMapper {

    private final ModelMapper modelMapper;

    private IngredientsDto mapIngredientToDto(Ingredients ingredient) {
        if (ingredient == null) return null;

        return IngredientsDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .nutrition(ingredient.getNutrition())
                .category(ingredient.getCategory())
                .quantity(ingredient.getQuantity())
                .build();
    }

    public RecipesDto toDto(Recipes recipe) {
        if (recipe == null) return null;

        return RecipesDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .description(recipe.getDescription())
                .fromAge(recipe.getFromAge())
                .toAge(recipe.getToAge())
                .ingredientsDtoList(
                        recipe.getIngredients() != null
                                ? recipe.getIngredients().stream()
                                .map(this::mapIngredientToDto)
                                .collect(Collectors.toList())
                                : new ArrayList<>()
                )
                .build();
    }

    public Recipes toEntity(RecipesDto dto) {
        if (dto == null) return null;

        Recipes recipe = modelMapper.map(dto, Recipes.class);

        if (dto.getIngredientsDtoList() != null) {
            List<Ingredients> ingredients = dto.getIngredientsDtoList().stream()
                    .map(ingDto -> {
                        Ingredients ing = new Ingredients();
                        ing.setId(ingDto.getId());
                        ing.setName(ingDto.getName());
                        ing.setQuantity(ingDto.getQuantity());
                        ing.setNutrition(ingDto.getNutrition());
                        ing.setCategory(ingDto.getCategory());
                        ing.setRecipe(recipe);
                        return ing;
                    })
                    .collect(Collectors.toList());
            recipe.setIngredients(ingredients);
        }

        return recipe;
    }
}
