package se.samuel.minimums.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Service.RecipesService;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    @Autowired
    private RecipesService service;

    @PostMapping
    public RecipesDto create(@RequestBody RecipesDto recipesDto) {
        return service.createRecipe(recipesDto);
    }




}
