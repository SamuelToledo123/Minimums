package se.samuel.minimums.Recipe;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Ingredients.Ingredients;
import se.samuel.minimums.Ingredients.IngredientsDto;
import java.util.ArrayList;




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
                .ingredients(
                        recipe.getIngredients() != null
                                ? recipe.getIngredients().stream()
                                .map(this::mapIngredientToDto)
                                .toList()
                                : new ArrayList<>()
                )
                .build();
    }



    public Recipes toEntity(RecipesDto recipesDto) {
        return modelMapper.map(recipesDto, Recipes.class);


    }
}


