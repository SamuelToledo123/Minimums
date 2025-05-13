package se.samuel.minimums.Ingredients;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
