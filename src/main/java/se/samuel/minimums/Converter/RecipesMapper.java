package se.samuel.minimums.Converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.Recipes;

@Component
@RequiredArgsConstructor
public class RecipesMapper {

    private final ModelMapper modelMapper;

    public RecipesDto toDto(Recipes recipes) {
        return modelMapper.map(recipes, RecipesDto.class);


    }

    public Recipes toEntity(RecipesDto recipesDto) {
        return modelMapper.map(recipesDto, Recipes.class);


    }
}
