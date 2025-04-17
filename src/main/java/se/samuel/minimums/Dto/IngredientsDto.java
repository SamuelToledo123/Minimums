package se.samuel.minimums.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientsDto {

    private Long id;
    private String name;
    private String nutrition;
    private String category;
    private double quantity;

    private RecipesDto recipe;
}
