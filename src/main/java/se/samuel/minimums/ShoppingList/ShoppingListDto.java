package se.samuel.minimums.ShoppingList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.AppUser.Dto.AppUserDto;
import se.samuel.minimums.Ingredients.IngredientsDto;
import se.samuel.minimums.MealPlan.MealPlanDto;

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
    private List<IngredientsDto> ingredients;

}
