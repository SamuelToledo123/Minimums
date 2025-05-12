package se.samuel.minimums.MealPlan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.Recipe.Recipes;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mealType;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<Recipes> recipes;

}
