package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildServiceTest {

    @ExtendWith(MockitoExtension.class)
    @Mock
    private ChildRepo repo;
    @Mock
    private RecipesRepo recipesrepo;

    @Mock
    private ChildMapper mapper;
    @Mock
    RecipesMapper recipesMapper;

    @InjectMocks
    private ChildService childService;

    private Child child;
    private ChildDto childDto;

    private Recipes recipes;

    @BeforeEach
    void setUp() {
        child = Child.builder()
                .id(1L)
                .name("Ella")
                .age(10)
                .allergies("Nuts")
                .build();
        childDto = ChildDto.builder()
                .id(1L)
                .name("Ella")
                .age(10)
                .allergies("Nuts")
                .build();

        recipes = Recipes.builder()
                .id(1L)
                .name("Carbonara")
                .fromAge(12)
                .toAge(16)
                .build();
    }
    @Test
    void getAllChildren() {
        when(repo.findAll()).thenReturn(List.of(child));
        when(mapper.ChildToChildDto(child)).thenReturn(childDto);

        List<ChildDto> result = childService.getAllChildren();

        assertEquals(1, result.size());
        assertEquals("Ella", result.get(0).getName());
        assertEquals("Nuts", result.get(0).getAllergies());
        assertEquals(10, result.get(0).getAge());

       //Result print
        System.out.println("Child Age: " + result.get(0).getName());
        verify(repo).findAll();
    }
    @Test
    void createChild() {

        when(mapper.ChildDtoToChild(childDto)).thenReturn(child);

        // Mock the save operation
        when(repo.save(child)).thenReturn(child);

        // Mock the mapping back from entity to DTO
        when(mapper.ChildToChildDto(child)).thenReturn(childDto);

        // Call the method
        ChildDto result = childService.createChild(childDto);

        // Verify
        assertNotNull(result);
        assertEquals("Ella", result.getName());
        assertEquals(10, result.getAge());
        assertEquals("Nuts", result.getAllergies());

        verify(mapper).ChildDtoToChild(childDto);
        verify(repo).save(child);
        verify(mapper).ChildToChildDto(child);
    }
    @Test
    void updateChild() {

        Child updatedEntity = Child.builder()
                .id(1L)
                .name("Maria")
                .allergies("Pollen")
                .age(12)
                .build();

        ChildDto expectedDto = ChildDto.builder()
                .id(1L)
                .name("Maria")
                .allergies("Pollen")
                .age(12)
                .build();


        //Find from setUp
        when(repo.findById(1L)).thenReturn(Optional.of(child));
        when(repo.save(child)).thenReturn(updatedEntity);
        when(mapper.ChildToChildDto(updatedEntity)).thenReturn(expectedDto);

        ChildDto result = childService.updateChild(1L, expectedDto);

        assertEquals("Maria", result.getName());
        assertEquals(12, result.getAge());
        assertEquals("Pollen", result.getAllergies());

        verify(repo).findById(1L);
        verify(repo).save(child);
        verify(mapper).ChildToChildDto(updatedEntity);

    }

    @Test
    void getChildById() {

        when(repo.findById(1L)).thenReturn(Optional.of(child));

        Optional<Child> result = childService.getChildById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ella", result.get().getName());
        assertEquals(10, result.get().getAge());
        assertEquals("Nuts", result.get().getAllergies());

    }
    @Test
    void deleteChild_Found() {
        Long id = 1L;
        when(repo.findById(1L)).thenReturn(Optional.of(child));

        String result = childService.deleteChild(1L);

        assertEquals("Child with id " + id + " deleted.", result);
        verify(repo).deleteById(id);

    }
    @Test
    void deleteChild_notFound() {
        Long id2 = 999L;
        when(repo.findById(id2)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> childService.deleteChild(id2));
        assertTrue(ex.getMessage().contains("Child by id " + id2 + " not found."));
    }
    @Test
    void addNewRecipeToChild() {
        Long childId = 1L;
        RecipesDto recipesDto = RecipesDto.builder()
                .name("Meatballs")
                .fromAge(6)
                .toAge(10)
                .build();

        // Mock the repository response for finding the child
        when(repo.findById(childId)).thenReturn(Optional.of(child));

        // Mock the recipe mapping
        when(recipesMapper.RecipesRecipesDtoToRecipes(any(RecipesDto.class))).thenReturn(recipes);

        // Call the method
        childService.addNewRecipeToChild(childId, recipesDto);

        // Verify the recipe is added to the child and saved
        assertEquals(1, child.getRecipes().size());
        verify(recipesrepo, times(1)).save(recipes);
    }

    @Test
    void addRecipeToChild() {
        Long childId = 1L;
        Long recipeId = 1L;

        // Mock the repository responses for finding child and recipe
        when(repo.findById(childId)).thenReturn(Optional.of(child));
        when(recipesrepo.findById(recipeId)).thenReturn(Optional.of(recipes));

        // Call the method
        childService.addRecipeToChild(childId, recipeId);

        // Verify the recipe's child is set and the recipe is added to the child's recipe list
        assertEquals(childId, recipes.getChild().getId());
        assertTrue(child.getRecipes().contains(recipes));
        verify(recipesrepo, times(1)).save(recipes);
    }
    @Test
    void deleteRecipeFromChild() {
        Long childId = 1L;
        Long recipeId = 1L;

        // Set up the relationship between child and recipe
        child.setRecipes(new ArrayList<>());
        child.getRecipes().add(recipes);
        recipes.setChild(child);

        // Mock the repository responses
        when(repo.findById(childId)).thenReturn(Optional.of(child));
        when(recipesrepo.findById(recipeId)).thenReturn(Optional.of(recipes));

        // Call the method
        childService.deleteRecipeFromChild(childId, recipeId);

        // Verify that the recipe was removed from the child's recipe list and unlinked from the child
        assertNull(recipes.getChild());
        assertFalse(child.getRecipes().contains(recipes));
        verify(recipesrepo, times(1)).save(recipes);
    }
}