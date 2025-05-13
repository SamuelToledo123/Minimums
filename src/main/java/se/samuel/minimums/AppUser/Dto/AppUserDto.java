package se.samuel.minimums.AppUser.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.MealPlan.MealPlanDto;
import se.samuel.minimums.ShoppingList.ShoppingListDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDto {

    private Long id;
    private String name;
    private String email;
    private List<MealPlanDto> mealPlans;
    private List<ShoppingListDto> shoppingList;
}
