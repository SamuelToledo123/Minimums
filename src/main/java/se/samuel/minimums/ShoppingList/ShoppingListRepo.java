package se.samuel.minimums.ShoppingList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.ShoppingList.ShoppingList;

@Repository
public interface ShoppingListRepo extends JpaRepository<ShoppingList, Long> {
}
