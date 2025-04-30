package se.samuel.minimums.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Service.ChildService;

import java.util.List;

@RestController
@RequestMapping("/api/child")
@RequiredArgsConstructor

public class ChildController {
    private final ChildService childService;

        @GetMapping
        public ResponseEntity<List<ChildDto>> getAllChildren() {
            return ResponseEntity.ok(childService.getAllChildren());
        }

        @PostMapping
        public ResponseEntity<ChildDto> createChild(@RequestBody ChildDto childDto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(childService.createChild(childDto));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ChildDto> getChildById(@PathVariable Long id) {
            return childService.getChildById(id)
                    .map(child -> ResponseEntity.ok().body(childService.getAllChildren()
                            .stream().filter(dto -> dto.getId().equals(id)).findFirst().orElse(null)))
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<ChildDto> updateChild(@PathVariable Long id, @RequestBody ChildDto updatedDto) {
            return ResponseEntity.ok(childService.updateChild(id, updatedDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteChild(@PathVariable Long id) {
            return ResponseEntity.ok(childService.deleteChild(id));
        }

        @PostMapping("/{childId}/recipes")
        public ResponseEntity<String> addNewRecipeToChild(@PathVariable Long childId, @RequestBody RecipesDto recipesDto) {
            childService.addNewRecipeToChild(childId, recipesDto);


            return ResponseEntity.ok("Recipe added to child.");
        }

        @PostMapping("/{childId}/recipes/{recipeId}")
        public ResponseEntity<String> addExistingRecipeToChild(@PathVariable Long childId, @PathVariable Long recipeId) {
            childService.addRecipeToChild(childId, recipeId);
            return ResponseEntity.ok("Existing recipe assigned to child.");
        }

        @DeleteMapping("/{childId}/recipes/{recipeId}")
        public ResponseEntity<String> deleteRecipeFromChild(@PathVariable Long childId, @PathVariable Long recipeId) {
            childService.deleteRecipeFromChild(childId, recipeId);
            return ResponseEntity.ok("Recipe removed from child.");
        }
    }
