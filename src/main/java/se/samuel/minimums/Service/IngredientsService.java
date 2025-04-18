package se.samuel.minimums.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Dto.IngredientsDto;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Repo.IngredientsRepo;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepo ingredientsRepo;
    @Autowired
    private IngredientsMapper ingredientsMapper;

    public List<IngredientsDto> getAllIngredients() {
        return ingredientsRepo.findAll().stream().map(ingredientsMapper::IngredientsToIngredientsDto)
                .toList();
    }

    public Optional<Ingredients> getIngredientById(Long id) {
        return ingredientsRepo.findById(id);
    }

    public IngredientsDto createIngredient(IngredientsDto ingredientsDto) {

        Ingredients newIngredient = ingredientsMapper.IngredientsDtoToIngredients(ingredientsDto);
       Ingredients savedIngredient = ingredientsRepo.save(newIngredient);

        return ingredientsMapper.IngredientsToIngredientsDto(savedIngredient);
    }

    public IngredientsDto updateIngredient(Long id, IngredientsDto updated) {

        Ingredients ingredient  = ingredientsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setNutrition(updated.getNutrition());

        Ingredients savedIngredients = ingredientsRepo.save(ingredient);

        return ingredientsMapper.IngredientsToIngredientsDto(savedIngredients);
    }

    public String deleteIngredient(Long id) {
        Ingredients ingredient = ingredientsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredientsRepo.deleteById(id);
        return "Ingredient with id " + id + " has been deleted.";
    }
}
