package se.samuel.minimums.ShoppingList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.MealPlan.MealPlan;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.Ingredients.Ingredients;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "mealplan")
    private MealPlan mealPlan;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
    private List<Ingredients> ingredients;

}
