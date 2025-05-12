package se.samuel.minimums.AppUser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.Child.Child;
import se.samuel.minimums.MealPlan.MealPlan;
import se.samuel.minimums.ShoppingList.ShoppingList;

import java.util.Set;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<MealPlan> mealPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShoppingList> shoppingList;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities;

}
