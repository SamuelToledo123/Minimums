package se.samuel.minimums.Ingredients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Recipe.RecipesDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientsDto {

    private Long id;
    private String name;
    private String nutrition;
    private String category;
    private String quantity;

    //private RecipesDto recipe;

}
