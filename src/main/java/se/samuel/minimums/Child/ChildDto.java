package se.samuel.minimums.Child;


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
public class ChildDto {

    private Long id;
    private String name;
    private int age;
    private String allergies;

    private List<RecipesDto> recommendedRecipes;

}
