package se.samuel.minimums.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Food> foods;

    @ManyToOne
    @JoinColumn(name = "mealplan_id")
    private MealPlan mealPlan;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
}
