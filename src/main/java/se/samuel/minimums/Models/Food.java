package se.samuel.minimums.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Food {

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

}
