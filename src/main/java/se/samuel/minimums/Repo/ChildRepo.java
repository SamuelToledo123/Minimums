package se.samuel.minimums.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Models.Child;

@Repository
public interface ChildRepo extends JpaRepository<Child, Long> {
}
