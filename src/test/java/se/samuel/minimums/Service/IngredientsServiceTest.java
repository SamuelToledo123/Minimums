package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Dto.IngredientsDto;
import se.samuel.minimums.Models.Ingredients;
import se.samuel.minimums.Repo.IngredientsRepo;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class IngredientsServiceTest {
    @ExtendWith(MockitoExtension.class)
    @Mock
    private IngredientsRepo repo;

    @Mock
    private IngredientsMapper mapper;

    @InjectMocks
    private IngredientsService ingredientsService;

    private Ingredients ingredients;
    private IngredientsDto ingredientsDto;


    @BeforeEach
    void setUp() {
        ingredients = Ingredients.builder()
                .id(1L)
                .name("Chicken")
                .nutrition("20 kcal")
                .quantity(2)
                .build();

        ingredientsDto = IngredientsDto.builder()
                .id(1L)
                .name("Chicken")
                .nutrition("20 kcal")
                .quantity(2)
                .build();


    }
    @Test
    void getAllIngredients() {
        when(repo.findAll()).thenReturn(List.of(ingredients));
        when(mapper.IngredientsToIngredientsDto(ingredients)).thenReturn(ingredientsDto);

        List<IngredientsDto> result = ingredientsService.getAllIngredients();

        assertEquals(1, result.size());
        assertEquals("Chicken", result.get(0).getName());
        assertEquals("20 kcal", result.get(0).getNutrition());
        assertEquals(2, result.get(0).getQuantity());
        verify(repo).findAll();

    }
    @Test
    void getIngredientById() {
        when(repo.findById(1L)).thenReturn(Optional.of(ingredients));

        Optional<Ingredients> result = ingredientsService.getIngredientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Chicken", result.get().getName());
        assertEquals("20 kcal", result.get().getNutrition());

    }
    @Test
    void createIngredient() {
        // Mock the mapping from DTO to entity
        when(mapper.IngredientsDtoToIngredients(ingredientsDto)).thenReturn(ingredients);

        // Mock the save operation
        when(repo.save(ingredients)).thenReturn(ingredients);

        // Mock the mapping back from entity to DTO
        when(mapper.IngredientsToIngredientsDto(ingredients)).thenReturn(ingredientsDto);

        // Call the method
        IngredientsDto result = ingredientsService.createIngredient(ingredientsDto);

        // Verify
        assertNotNull(result);
        assertEquals("Chicken", result.getName());
        assertEquals(2, result.getQuantity());
        assertEquals("20 kcal", result.getNutrition());

        verify(mapper).IngredientsDtoToIngredients(ingredientsDto);
        verify(repo).save(ingredients);
        verify(mapper).IngredientsToIngredientsDto(ingredients);


    }
    @Test
    void updateIngredient() {


        IngredientsDto updatedDto = new IngredientsDto();
        updatedDto.setName("Beef");
        updatedDto.setQuantity(3);
        updatedDto.setNutrition("30 kcal");


        Ingredients updatedEntity = Ingredients.builder()
                .id(1L)
                .name("Beef")
                .quantity(3)
                .nutrition("30 kcal")
                .build();

        IngredientsDto expectedDto = new IngredientsDto();
        expectedDto.setId(1L);
        expectedDto.setName("Beef");
        expectedDto.setQuantity(3);
        expectedDto.setNutrition("30 kcal");

        //Find from setUp
        when(repo.findById(1L)).thenReturn(Optional.of(ingredients));
        when(repo.save(ingredients)).thenReturn(updatedEntity);
        when(mapper.IngredientsToIngredientsDto(updatedEntity)).thenReturn(expectedDto);

        IngredientsDto result = ingredientsService.updateIngredient(1L, updatedDto);

        assertEquals("Beef", result.getName());
        assertEquals(3, result.getQuantity());
        assertEquals("30 kcal", result.getNutrition());

        verify(repo).findById(1L);
        verify(repo).save(ingredients);
        verify(mapper).IngredientsToIngredientsDto(updatedEntity);


    }

    @Test
    void deleteIngredient_Found() {

        Long id = 1L;
        when(repo.findById(1L)).thenReturn(Optional.of(ingredients));

        String result = ingredientsService.deleteIngredient(1L);

        assertEquals("Ingredient with id 1 has been deleted.", result);
        verify(repo).deleteById(id);

    }

    @Test
    void deleteIngredient_notFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> ingredientsService.deleteIngredient(999L));
        assertTrue(ex.getMessage().contains("Ingredient not found"));
    }
}