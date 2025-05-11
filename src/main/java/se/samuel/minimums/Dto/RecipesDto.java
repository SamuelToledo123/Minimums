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
