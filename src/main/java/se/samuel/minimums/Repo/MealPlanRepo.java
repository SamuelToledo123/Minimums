package se.samuel.minimums.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.Models.MealPlan;

@Repository
public interface MealPlanRepo extends JpaRepository<MealPlan, Long> {
}
