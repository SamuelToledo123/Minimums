package se.samuel.minimums.Ingredients;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Recipe.Recipes;
import se.samuel.minimums.ShoppingList.ShoppingList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nutrition;
    private String category;
    private double quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipes recipe;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

}
