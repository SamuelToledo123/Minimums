package se.samuel.minimums.MealPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.samuel.minimums.MealPlan.MealPlan;

@Repository
public interface MealPlanRepo extends JpaRepository<MealPlan, Long> {
}
