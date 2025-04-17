package se.samuel.minimums.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Services.IngredientsService;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {


    @Autowired
    IngredientsService ingredientsService;
    @PostMapping
    public Ingredients create(@RequestBody Ingredients ingredient) {
        return ingredientsService.createIngredient(ingredient);
    }


}
