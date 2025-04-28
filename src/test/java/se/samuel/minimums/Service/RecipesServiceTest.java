package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Converter.RecipesMapper;
import se.samuel.minimums.Dto.IngredientsDto;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.RecipesRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
    class RecipesServiceTest {

        @Mock
        private RecipesRepo recipesRepo;

        @Mock
        private RecipesMapper recipesMapper;

        @Mock
        private IngredientsMapper ingredientsMapper;

        @InjectMocks
        private RecipesService recipesService;

        private Recipes recipe;
        private RecipesDto recipeDto;



        @BeforeEach
        void setUp() {
            recipe = Recipes.builder()
                    .id(1L)
                    .name("Pasta")
                    .description("Yummy pasta")
                    .instructions("Boil water...")
                    .fromAge(2)
                    .toAge(5)
                    .ingredients(new ArrayList<>())
                    .build();

            recipeDto = RecipesDto.builder()
                    .id(1L)
                    .name("Pasta")
                    .description("Yummy pasta")
                    .instructions("Boil water...")
                    .fromAge(2)
                    .toAge(5)
                    .ingredientsDtoList(new ArrayList<>())
                    .build();


        }

        @Test
        void testGetAllRecipes() {
            when(recipesRepo.findAll()).thenReturn(List.of(recipe));
            when(recipesMapper.toDto(recipe)).thenReturn(recipeDto);

            List<RecipesDto> result = recipesService.getAllRecipes();

            assertEquals(1, result.size());
            verify(recipesRepo).findAll();
        }

        @Test
        void testGetRecipeById() {
            when(recipesRepo.findById(1L)).thenReturn(Optional.of(recipe));
            when(recipesMapper.toDto(recipe)).thenReturn(recipeDto);

            Optional<RecipesDto> result = recipesService.getRecipeById(1L);

            assertTrue(result.isPresent());
            assertEquals("Pasta", result.get().getName());
        }

        @Test
        void testCreateRecipe() {
            when(recipesMapper.toEntity(recipeDto)).thenReturn(recipe);
            when(recipesRepo.save(recipe)).thenReturn(recipe);
            when(recipesMapper.toDto(recipe)).thenReturn(recipeDto);

            RecipesDto result = recipesService.createRecipe(recipeDto);

            assertEquals("Pasta", result.getName());
            verify(recipesRepo).save(recipe);
        }

        @Test
        void testUpdateRecipe() {


            Ingredients ingredientEntity = Ingredients.builder()
                    .name("Salt")
                    .build();

                IngredientsDto ingredientsDto = IngredientsDto.builder()
                        .name("Salt")
                        .build();

                RecipesDto updateDto = RecipesDto.builder()
                        .name("Updated")
                        .description("Updated Desc")
                        .instructions("Updated Instructions")
                        .fromAge(1)
                        .toAge(3)
                        .ingredientsDtoList(List.of(ingredientsDto))
                        .build();

                recipe.setIngredients(new ArrayList<>()); // make sure it's not null

                when(recipesRepo.findById(1L)).thenReturn(Optional.of(recipe));
                when(ingredientsMapper.toEntity(ingredientsDto)).thenReturn(ingredientEntity);
                when(recipesMapper.toDto(any())).thenReturn(updateDto);

                RecipesDto result = recipesService.updateRecipe(1L, updateDto);

                assertEquals("Updated", result.getName());
                verify(recipesRepo).save(recipe);
            }
            @Test
        void testDeleteRecipe() {
            when(recipesRepo.existsById(1L)).thenReturn(true);

            recipesService.deleteRecipe(1L);

            verify(recipesRepo).deleteById(1L);
        }

        @Test
        void testDeleteRecipeThrowsWhenNotExists() {
            when(recipesRepo.existsById(999L)).thenReturn(false);

            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                    recipesService.deleteRecipe(999L));

            assertTrue(exception.getMessage().contains("Recipe not found"));
        }
    }
