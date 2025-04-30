package se.samuel.minimums.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Converter.ChildMapper;
import se.samuel.minimums.Converter.RecipesMapper;
import se.samuel.minimums.Dto.ChildDto;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.Child;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.ChildRepo;
import se.samuel.minimums.Repo.RecipesRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepo childRepo;
    private final RecipesRepo recipesRepo;
    private final ChildMapper childMapper;
    private final RecipesMapper recipesMapper;

    public List<ChildDto> getAllChildren() {
        return childRepo.findAll()
                .stream()
                .map(childMapper::toDto).toList();

    }

    public ChildDto createChild(ChildDto childDto) {
        Child newChild = childMapper.toEntity(childDto);
        Child savedChild = childRepo.save(newChild);
        return childMapper.toDto(savedChild);
    }

    public Optional<Child> getChildById(Long id) {
       return childRepo.findById(id);
    }

    public ChildDto updateChild(Long id, ChildDto updatedDto) {
        Child child = childRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        child.setName(updatedDto.getName());
        child.setAge(updatedDto.getAge());
        child.setAllergies(updatedDto.getAllergies());
        Child savedChild = childRepo.save(child);
        // UPPDATERA SENARE MED RECEPT & REKOMMENDATIONER
        return childMapper.toDto(savedChild);
    }

    public String deleteChild(Long id) {
        childRepo.findById(id).orElseThrow(() -> new RuntimeException("Child by id " + id + " not found."));
        childRepo.deleteById(id);
        return "Child with id " + id + " deleted.";
    }

    public void addNewRecipeToChild(Long childId, RecipesDto recipesDto) {
        Child child = childRepo.findById(childId).orElseThrow(() ->
                new RuntimeException("Child not found with id: " + childId));

        Recipes recipes = recipesMapper.toEntity(recipesDto);

        recipes.setChild(child);
        recipesRepo.save(recipes);
    }

    public void addRecipeToChild(Long childId, Long recipeId) {
        Child child = childRepo.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + childId));

        Recipes recipe = recipesRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        recipe.setChild(child);
        if (child.getRecipes() == null) {
            child.setRecipes(new ArrayList<>());
        }
        if (!child.getRecipes().contains(recipe)) {
            child.getRecipes().add(recipe);
        }
        recipesRepo.save(recipe);
    }
    public void deleteRecipeFromChild(Long childId, Long recipeId) {
        Child child = childRepo.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + childId));

        Recipes recipe = recipesRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        if (recipe.getChild() != null && recipe.getChild().getId().equals(childId)) {
            recipe.setChild(null);

            if (child.getRecipes() != null) {
                child.getRecipes().remove(recipe);
            }

            recipesRepo.save(recipe);
        } else {
            throw new RuntimeException("Recipe is not assigned to this child.");
        }
    }







}
