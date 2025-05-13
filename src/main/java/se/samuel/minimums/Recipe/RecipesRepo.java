package se.samuel.minimums.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Recipe.Recipes;

import java.util.Optional;

@Repository
public interface RecipesRepo extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findByName(String name);
}
