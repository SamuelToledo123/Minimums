package se.samuel.minimums.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Models.Recipes;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipesRepo extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findByName(String name);
}
