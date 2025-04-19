package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.MealPlanMapper;
import se.samuel.minimums.Dto.AppUserDto;
import se.samuel.minimums.Dto.MealPlanDto;
import se.samuel.minimums.Dto.RecipesDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Models.MealPlan;
import se.samuel.minimums.Models.Recipes;
import se.samuel.minimums.Repo.AppUserRepo;
import se.samuel.minimums.Repo.MealPlanRepo;
import se.samuel.minimums.Repo.RecipesRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MealPlanServiceTest {

    @Mock
    private MealPlanRepo mealPlanRepo;
    @Mock
    private MealPlanMapper mealPlanMapper;
    @Mock
    private AppUserRepo appUserRepo;
    @Mock
    private RecipesRepo recipesRepo;
    @InjectMocks
    private MealPlanService mealPlanService;

    private MealPlan mealPlan;
    private MealPlanDto mealPlanDto;
    private AppUser mockUser;
    private Recipes mockRecipe;

    private AppUserDto mockUserDto;
    private RecipesDto mockRecipeDto;


    @BeforeEach
    void setUp() {

        mockUser = mock(AppUser.class);
        mockRecipe = mock(Recipes.class);

        mockUserDto = mock(AppUserDto.class);
        mockRecipeDto = mock(RecipesDto.class);

        mealPlan = MealPlan.builder()
                .id(1L)
                .mealType("Lunch")
                .date("2025-04-19")
                .user(mockUser)
                .recipes(Collections.singletonList(mockRecipe))
                .build();

        mealPlanDto = MealPlanDto.builder()
                .id(1L)
                .mealType("Lunch")
                .date("2025-04-19")
                .appUser(mockUserDto)
                .recipes(Collections.singletonList(mockRecipeDto))
                .build();
    }

    @Test
    void getAllMealPlans_returnsDtoList() {
        when(mealPlanRepo.findAll()).thenReturn(List.of(mealPlan));
        when(mealPlanMapper.toDto(mealPlan)).thenReturn(mealPlanDto);

        List<MealPlanDto> result = mealPlanService.getAllMealPlans();

        assertEquals(1, result.size());
        assertEquals("Lunch", result.get(0).getMealType());
        verify(mealPlanRepo).findAll();
    }

    @Test
    void getMealPlanById_returnsOptionalMealPlan() {
        when(mealPlanRepo.findById(1L)).thenReturn(Optional.of(mealPlan));

        Optional<MealPlan> result = mealPlanService.findMealPlanById(1L);

        assertTrue(result.isPresent());
        assertEquals("Lunch", result.get().getMealType());
        verify(mealPlanRepo).findById(1L);
    }

    @Test
    void createMealPlan_success() {
        when(mockUserDto.getEmail()).thenReturn("test@example.com");
        when(appUserRepo.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(mockRecipeDto.getId()).thenReturn(1L);
        when(recipesRepo.findById(1L)).thenReturn(Optional.of(mockRecipe));
        when(mealPlanMapper.toEntity(mealPlanDto)).thenReturn(mealPlan);
        when(mealPlanRepo.save(mealPlan)).thenReturn(mealPlan);
        when(mealPlanMapper.toDto(mealPlan)).thenReturn(mealPlanDto);

        MealPlanDto result = mealPlanService.createMealPlan(mealPlanDto);

        assertNotNull(result);
        assertEquals("Lunch", result.getMealType());

        verify(appUserRepo).findByEmail("test@example.com");
        verify(recipesRepo).findById(1L);
        verify(mealPlanRepo).save(mealPlan);
        verify(mealPlanMapper).toDto(mealPlan);
    }

    @Test
    void updateMealPlan_success() {
        MealPlan updatedMealPlan = MealPlan.builder()
                .id(1L)
                .mealType("Dinner")
                .date("2025-04-20")
                .user(mockUser)
                .recipes(Collections.singletonList(mockRecipe))
                .build();

        MealPlanDto updatedDto = MealPlanDto.builder()
                .id(1L)
                .mealType("Dinner")
                .date("2025-04-20")
                .appUser(mockUserDto)
                .recipes(Collections.singletonList(mockRecipeDto))
                .build();

        when(mockUserDto.getEmail()).thenReturn("test@example.com");
        when(appUserRepo.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(mealPlanRepo.findById(1L)).thenReturn(Optional.of(mealPlan));
        when(recipesRepo.findById(1L)).thenReturn(Optional.of(mockRecipe));

        when(mealPlanRepo.save(any(MealPlan.class))).thenReturn(updatedMealPlan);
        when(mealPlanMapper.toDto(updatedMealPlan)).thenReturn(updatedDto);
        when(mockRecipeDto.getId()).thenReturn(1L);

        MealPlanDto result = mealPlanService.updateMealPlan(1L, updatedDto);

        assertEquals("Dinner", result.getMealType());
        verify(mealPlanRepo).save(any(MealPlan.class));
        verify(appUserRepo).findByEmail("test@example.com");
        verify(recipesRepo).findById(1L);
        verify(mealPlanMapper).toDto(updatedMealPlan);
    }

    @Test
    void deleteMealPlan_success() {
        when(mealPlanRepo.findById(1L)).thenReturn(Optional.of(mealPlan));
        mealPlanService.deleteMealPlan(1L);

        verify(mealPlanRepo).delete(mealPlan);
    }
}




