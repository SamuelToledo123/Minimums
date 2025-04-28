package se.samuel.minimums.Converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.MealPlanDto;
import se.samuel.minimums.Models.MealPlan;

@Component
@RequiredArgsConstructor
public class MealPlanMapper {

    private final ModelMapper modelMapper;
    private final AppUserMapper appUserMapper;
    private final RecipesMapper recipeMapper;

    public MealPlan toEntity(MealPlanDto dto) {
        return modelMapper.map(dto, MealPlan.class);
    }


    public MealPlanDto toDto(MealPlan mealPlan) {
        MealPlanDto dto = modelMapper.map(mealPlan, MealPlanDto.class);
        dto.setAppUser(appUserMapper.toDto(mealPlan.getUser()));
        dto.setRecipes(mealPlan.getRecipes().stream()
                .map(recipeMapper::toDto)
                .toList());
        return dto;
    }
}
