package se.samuel.minimums.AppUser.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Child.ChildDto;
import se.samuel.minimums.MealPlan.MealPlanDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProfileDto {
    private String name;
    private String email;
    private List<ChildDto> children;
    private List<MealPlanDto> mealPlans;

}
