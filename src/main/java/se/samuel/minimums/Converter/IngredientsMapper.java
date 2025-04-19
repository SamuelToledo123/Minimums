package se.samuel.minimums.Converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.IngredientsDto;
import se.samuel.minimums.Models.Ingredients;

@Component
@RequiredArgsConstructor
public class IngredientsMapper {

    private final ModelMapper modelMapper;

    public IngredientsDto toDto(Ingredients ingredients) {
        return modelMapper.map(ingredients, IngredientsDto.class);
    }
    public Ingredients toEntity(IngredientsDto ingredientsDto) {
        return modelMapper.map(ingredientsDto, Ingredients.class);
    }
}
