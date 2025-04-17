package se.samuel.minimums.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Repo.IngredientsRepo;

import java.util.List;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepo ingredientsRepo;

    public List<Ingredients> getAllIngredients() {
        return ingredientsRepo.findAll();
    }

    public Ingredients getIngredientById(Long id) {
        return ingredientsRepo.findById(id).orElse(null);
    }

    public Ingredients createIngredient(Ingredients ingredient) {
        return ingredientsRepo.save(ingredient);
    }

    public Ingredients updateIngredient(Long id, Ingredients updated) {
        Ingredients ingredient  = ingredientsRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setNutrition(updated.getNutrition());
        ingredientsRepo.save(ingredient);
        return ingredient;
    }

    public void deleteIngredient(Long id) {
        ingredientsRepo.deleteById(id);
    }
}
