package se.samuel.minimums.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Models.Ingredients;

@Repository
public interface FoodRepo extends JpaRepository<Ingredients, Long> {
}
