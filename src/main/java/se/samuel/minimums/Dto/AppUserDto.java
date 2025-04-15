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
public class AppUserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<MealPlanDto> mealPlans;
    private List<ShoppingListDto> shoppinglist;
}
