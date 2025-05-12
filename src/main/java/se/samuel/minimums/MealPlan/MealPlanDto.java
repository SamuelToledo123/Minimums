package se.samuel.minimums.MealPlan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Recipe.RecipesDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealPlanDto {

    private Long id;
    private String mealType;
    private String date;

    private List<RecipesDto> recipes;
}
