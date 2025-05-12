package se.samuel.minimums.Recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.samuel.minimums.Recipe.RecipeController;
import se.samuel.minimums.Recipe.RecipesDto;
import se.samuel.minimums.Recipe.RecipesService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

class RecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecipesService recipesService;

    @InjectMocks
    private RecipeController recipeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllRecipes() throws Exception {
        RecipesDto recipe1 = RecipesDto.builder()
                .id(1L)
                .name("Pancakes")
                .instructions("Mix and cook")
                .description("Delicious")
                .fromAge(2)
                .toAge(5)
                .ingredients(null)
                .child(null)
                .build();

        RecipesDto recipe2 = RecipesDto.builder()
                .id(2L)
                .name("Omelette")
                .instructions("Whisk and fry")
                .description("Egg-based")
                .fromAge(3)
                .toAge(6)
                .ingredients(null)
                .child(null)
                .build();

        when(recipesService.getAllRecipes()).thenReturn(Arrays.asList(recipe1, recipe2));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pancakes"))
                .andExpect(jsonPath("$[1].name").value("Omelette"));
    }

    @Test
    void testGetRecipeById() throws Exception {
        RecipesDto recipe = RecipesDto.builder()
                .id(1L)
                .name("Pancakes")
                .instructions("Mix and cook")
                .description("Delicious")
                .fromAge(2)
                .toAge(5)
                .ingredients(null)
                .child(null)
                .build();

        when(recipesService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pancakes"))
                .andExpect(jsonPath("$.description").value("Delicious"));
    }

    @Test
    void testGetRecipeByIdNotFound() throws Exception {
        when(recipesService.getRecipeById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateRecipe() throws Exception {
        RecipesDto recipe = RecipesDto.builder()
                .name("Banana Pancakes")
                .instructions("Mix and cook")
                .description("Yummy")
                .fromAge(2)
                .toAge(5)
                .ingredients(null)
                .child(null)
                .build();

        RecipesDto createdRecipe = RecipesDto.builder()
                .id(1L)
                .name("Banana Pancakes")
                .instructions("Mix and cook")
                .description("Yummy")
                .fromAge(2)
                .toAge(5)
                .ingredients(null)
                .child(null)
                .build();

        when(recipesService.createRecipe(any(RecipesDto.class))).thenReturn(createdRecipe);

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Banana Pancakes"));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        RecipesDto updatedRecipe = RecipesDto.builder()
                .id(1L)
                .name("Updated Pancakes")
                .instructions("Mix and fry")
                .description("Tasty")
                .fromAge(3)
                .toAge(6)
                .ingredients(null)
                .child(null)
                .build();

        when(recipesService.updateRecipe(eq(1L), any(RecipesDto.class))).thenReturn(updatedRecipe);

        mockMvc.perform(put("/api/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Pancakes"));
    }

    @Test
    void testUpdateRecipeNotFound() throws Exception {
        RecipesDto updatedRecipe = RecipesDto.builder()
                .id(1L)
                .name("Updated Pancakes")
                .instructions("Mix and fry")
                .description("Tasty")
                .fromAge(3)
                .toAge(6)
                .ingredients(null)
                .child(null)
                .build();

        when(recipesService.updateRecipe(eq(1L), any(RecipesDto.class)))
                .thenThrow(new RuntimeException("Recipe not found"));

        mockMvc.perform(put("/api/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRecipe() throws Exception {
        doNothing().when(recipesService).deleteRecipe(1L);

        mockMvc.perform(delete("/api/recipes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRecipeNotFound() throws Exception {
        doThrow(new RuntimeException("Recipe not found")).when(recipesService).deleteRecipe(1L);

        mockMvc.perform(delete("/api/recipes/1"))
                .andExpect(status().isNotFound());
    }
}
