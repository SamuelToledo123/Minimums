package se.samuel.minimums.Recipe;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Child.Child;
import se.samuel.minimums.MealPlan.MealPlan;
import se.samuel.minimums.Ingredients.Ingredients;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String instructions;
    private String description;
    private int fromAge;
    private int toAge;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredients> ingredients;

    @ManyToOne
    @JoinColumn(name = "mealplan_id")
    private MealPlan mealPlan;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
}
