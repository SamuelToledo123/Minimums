package se.samuel.minimums.Child;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Child.Child;

@Repository
public interface ChildRepo extends JpaRepository<Child, Long> {
}
