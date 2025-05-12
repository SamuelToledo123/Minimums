package se.samuel.minimums.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Child.ChildDto;
import se.samuel.minimums.Ingredients.IngredientsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipesDto {

    private Long id;
    private String name;
    private String instructions;
    private String description;
    private int fromAge;
    private int toAge;

    private List<IngredientsDto> ingredientsDtoList;
    private ChildDto child;
}
