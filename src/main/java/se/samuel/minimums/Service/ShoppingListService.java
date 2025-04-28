package se.samuel.minimums.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.AppUserMapper;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Converter.ShoppingListMapper;
import se.samuel.minimums.Dto.ShoppingListDto;

import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Models.ShoppingList;
import se.samuel.minimums.Repo.ShoppingListRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {
    @Autowired
    private ShoppingListRepo shoppingListRepository;

    @Autowired
    private ShoppingListMapper shoppingListMapper;

    @Autowired
    private IngredientsMapper ingredientsMapper;
    @Autowired
    private AppUserMapper appUserMapper;

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

        if (entity.getIngredients() != null) {
            entity.getIngredients().forEach(ingredient -> ingredient.setShoppingList(entity));
        }

        ShoppingList saved = shoppingListRepository.save(entity);
        return shoppingListMapper.toDto(saved);
    }

    public ShoppingListDto updateShoppingList(Long id, ShoppingListDto dto) {
        ShoppingList existing = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list not found with id: " + id));

        existing.setDate(dto.getDate());
        existing.setUser(appUserMapper.toEntity(dto.getUser()));

        //Map to MealPlan after LAP-4 is merged & finished to main
        // existing.setMealPlan(dto.getMealPlan());

        if (dto.getIngredients() != null) {
            existing.getIngredients().clear();
            List<Ingredients> newIngredients = dto.getIngredients().stream()
                    .map(ingredientsMapper::toEntity)
                    .peek(i -> i.setShoppingList(existing))
                    .toList();
            existing.getIngredients().addAll(newIngredients);
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
