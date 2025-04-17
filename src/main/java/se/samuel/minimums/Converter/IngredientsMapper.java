package se.samuel.minimums.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.IngredientsDto;
import se.samuel.minimums.Models.Ingredients;

@Component
public class IngredientsMapper {

    @Autowired
    private ModelMapper modelMapper;


    public IngredientsDto IngredientsToIngredientsDto(Ingredients ingredients) {
        return modelMapper.map(ingredients, IngredientsDto.class);
    }
    public Ingredients IngredientsDtoToIngredients(IngredientsDto ingredientsDto) {
        return modelMapper.map(ingredientsDto, Ingredients.class);
    }
}
