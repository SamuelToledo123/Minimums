package se.samuel.minimums.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Converter.ShoppingListMapper;
import se.samuel.minimums.Dto.ShoppingListDto;
import se.samuel.minimums.Models.*;
import se.samuel.minimums.Repo.AppUserRepo;
import se.samuel.minimums.Repo.MealPlanRepo;
import se.samuel.minimums.Repo.ShoppingListRepo;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepo shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;
    private final IngredientsMapper ingredientsMapper;
    private final MealPlanRepo mealPlanRepo;
    private final AppUserRepo appUserRepo;

    public List<ShoppingListDto> getAllShoppingLists() {
        return shoppingListRepository.findAll()
                .stream()
                .map(shoppingListMapper::toDto)
                .toList();
    }

    public Optional<ShoppingListDto> getShoppingListById(Long id) {
        return shoppingListRepository.findById(id)
                .map(shoppingListMapper::toDto);
    }

    public ShoppingListDto createShoppingList(ShoppingListDto dto) {
        ShoppingList entity = shoppingListMapper.toEntity(dto);

        AppUser appUser = appUserRepo.findByEmail(dto.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User with email: " + dto.getUser().getEmail() + " not found"));
        entity.setUser(appUser);

        MealPlan mealPlan = mealPlanRepo.findById(dto.getMealPlan().getId())
                .orElseThrow(() -> new RuntimeException("MealPlan with ID: " + dto.getMealPlan().getId() + " not found"));
        entity.setMealPlan(mealPlan);

        if (dto.getIngredients() != null) {
            List<Ingredients> ingredients = dto.getIngredients().stream()
                    .map(ingredientsMapper::toEntity)
                    .peek(i -> i.setShoppingList(entity))
                    .toList();
            entity.setIngredients(ingredients);
        }

        ShoppingList saved = shoppingListRepository.save(entity);
        return shoppingListMapper.toDto(saved);
    }

    public ShoppingListDto updateShoppingList(Long id, ShoppingListDto dto) {
        ShoppingList existing = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list not found with id: " + id));

        existing.setDate(dto.getDate());

        if (dto.getUser() != null && dto.getUser().getEmail() != null) {
            AppUser appUser = appUserRepo.findByEmail(dto.getUser().getEmail())
                    .orElseThrow(() -> new RuntimeException("User with email: " + dto.getUser().getEmail() + " not found"));
            existing.setUser(appUser);
        }

        if (dto.getMealPlan() != null && dto.getMealPlan().getId() != null) {
            MealPlan mealPlan = mealPlanRepo.findById(dto.getMealPlan().getId())
                    .orElseThrow(() -> new RuntimeException("MealPlan with ID: " + dto.getMealPlan().getId() + " not found"));
            existing.setMealPlan(mealPlan);
        }

        if (dto.getIngredients() != null) {
            existing.getIngredients().clear();
            List<Ingredients> newIngredients = dto.getIngredients().stream()
                    .map(ingredientsMapper::toEntity)
                    .peek(i -> i.setShoppingList(existing))
                    .toList();
            existing.setIngredients(newIngredients);
        }

        ShoppingList updated = shoppingListRepository.save(existing);
        return shoppingListMapper.toDto(updated);
    }


    public void deleteShoppingList(Long id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new RuntimeException("Shopping list not found with id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }


}
