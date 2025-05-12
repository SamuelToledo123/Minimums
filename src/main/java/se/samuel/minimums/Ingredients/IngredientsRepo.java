package se.samuel.minimums.Ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Ingredients.Ingredients;

@Repository
public interface IngredientsRepo extends JpaRepository<Ingredients, Long> {
}
