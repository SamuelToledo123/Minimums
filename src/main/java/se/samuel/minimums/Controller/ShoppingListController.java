package se.samuel.minimums.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.ShoppingListDto;
import se.samuel.minimums.Service.ShoppingListService;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping
    public List<ShoppingListDto> getAllShoppingLists() {
        return shoppingListService.getAllShoppingLists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListDto> getShoppingListById(@PathVariable Long id) {
        return shoppingListService.getShoppingListById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShoppingListDto> createShoppingList(@RequestBody ShoppingListDto dto) {
        ShoppingListDto created = shoppingListService.createShoppingList(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListDto> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingListDto dto) {
        try {
            ShoppingListDto updated = shoppingListService.updateShoppingList(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        try {
            shoppingListService.deleteShoppingList(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

