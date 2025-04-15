package se.samuel.minimums.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Models.ShoppingList;

@Repository
public interface ShoppingListRepo extends JpaRepository<ShoppingList, Long> {
}
