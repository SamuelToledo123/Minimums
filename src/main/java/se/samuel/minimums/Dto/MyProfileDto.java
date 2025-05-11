package se.samuel.minimums.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
