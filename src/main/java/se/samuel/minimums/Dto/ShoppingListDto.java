package se.samuel.minimums.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListDto {

    private Long id;
    private String date;

    private AppUserDto user;
    private MealPlanDto mealPlan;
    private List<IngredientsDto> foods;

}
