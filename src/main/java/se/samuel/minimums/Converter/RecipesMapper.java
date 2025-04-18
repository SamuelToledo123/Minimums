package se.samuel.minimums.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.Recipes;

@Component
public class RecipesMapper {

    @Autowired
    ModelMapper modelMapper;

    public RecipesDto RecipesToRecipesDto(Recipes recipes) {
        return modelMapper.map(recipes, RecipesDto.class);


    }

    public Recipes RecipesRecipesDtoToRecipes(RecipesDto recipesDto) {
        return modelMapper.map(recipesDto, Recipes.class);


    }



}
