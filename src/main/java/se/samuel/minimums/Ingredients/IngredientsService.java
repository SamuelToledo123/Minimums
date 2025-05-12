package se.samuel.minimums.Ingredients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsService {

    private final IngredientsRepo ingredientsRepo;
    private final IngredientsMapper ingredientsMapper;

    public List<IngredientsDto> getAllIngredients() {
        return ingredientsRepo.findAll().stream().map(ingredientsMapper::toDto)
                .toList();
    }

    public Optional<Ingredients> getIngredientById(Long id) {
        return ingredientsRepo.findById(id);
    }

    public IngredientsDto createIngredient(IngredientsDto ingredientsDto) {

        Ingredients newIngredient = ingredientsMapper.toEntity(ingredientsDto);
        Ingredients savedIngredient = ingredientsRepo.save(newIngredient);

        return ingredientsMapper.toDto(savedIngredient);
    }

    public IngredientsDto updateIngredient(Long id, IngredientsDto updated) {

        Ingredients ingredient  = ingredientsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredient.setName(updated.getName());
        ingredient.setQuantity(updated.getQuantity());
        ingredient.setNutrition(updated.getNutrition());

        Ingredients savedIngredients = ingredientsRepo.save(ingredient);

        return ingredientsMapper.toDto(savedIngredients);
    }

    public String deleteIngredient(Long id) {
        Ingredients ingredient = ingredientsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredientsRepo.deleteById(id);
        return "Ingredient with id " + id + " has been deleted.";
    }
}
