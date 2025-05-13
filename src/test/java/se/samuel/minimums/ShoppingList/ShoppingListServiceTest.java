package se.samuel.minimums.ShoppingList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.ShoppingList.ShoppingListMapper;
import se.samuel.minimums.AppUser.Dto.AppUserDto;
import se.samuel.minimums.MealPlan.MealPlanDto;
import se.samuel.minimums.ShoppingList.ShoppingListDto;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.MealPlan.MealPlan;
import se.samuel.minimums.ShoppingList.ShoppingList;
import se.samuel.minimums.AppUser.AppUserRepo;
import se.samuel.minimums.MealPlan.MealPlanRepo;
import se.samuel.minimums.ShoppingList.ShoppingListRepo;
import se.samuel.minimums.ShoppingList.ShoppingListService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    class ShoppingListServiceTest {

        @InjectMocks
        private ShoppingListService shoppingListService;

        @Mock
        private ShoppingListRepo shoppingListRepository;

        @Mock
        private ShoppingListMapper shoppingListMapper;
        @Mock
        AppUserRepo appUserRepo;
        @Mock
        MealPlanRepo mealPlanRepo;

        @Test
        void testGetAllShoppingLists() {
            ShoppingList shoppingList = new ShoppingList();
            ShoppingListDto dto = new ShoppingListDto();

            when(shoppingListRepository.findAll()).thenReturn(List.of(shoppingList));
            when(shoppingListMapper.toDto(shoppingList)).thenReturn(dto);

            List<ShoppingListDto> result = shoppingListService.getAllShoppingLists();

            assertEquals(1, result.size());
            verify(shoppingListRepository).findAll();
            System.out.println(result);
        }

        @Test
        void testGetShoppingListById() {
            ShoppingList shoppingList = new ShoppingList();
            ShoppingListDto dto = new ShoppingListDto();

            when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
            when(shoppingListMapper.toDto(shoppingList)).thenReturn(dto);

            Optional<ShoppingListDto> result = shoppingListService.getShoppingListById(1L);

            assertTrue(result.isPresent());
            verify(shoppingListRepository).findById(1L);
        }

        @Test
        void testCreateShoppingList() {
            ShoppingListDto dto = new ShoppingListDto();
            AppUserDto userDto = new AppUserDto();
            userDto.setEmail("test@example.com");
            dto.setUser(userDto);

            MealPlanDto mealPlanDto = new MealPlanDto();
            mealPlanDto.setId(1L);
            dto.setMealPlan(mealPlanDto);

            ShoppingList entity = new ShoppingList();

            AppUser appUser = new AppUser();
            appUser.setEmail("test@example.com");

            MealPlan mealPlan = new MealPlan();
            mealPlan.setId(1L);

            when(shoppingListMapper.toEntity(dto)).thenReturn(entity);
            when(appUserRepo.findByEmail("test@example.com")).thenReturn(Optional.of(appUser));
            when(mealPlanRepo.findById(1L)).thenReturn(Optional.of(mealPlan));
            when(shoppingListRepository.save(entity)).thenReturn(entity);
            when(shoppingListMapper.toDto(entity)).thenReturn(dto);

            ShoppingListDto result = shoppingListService.createShoppingList(dto);

            assertEquals(dto, result);
            verify(shoppingListRepository).save(entity);
        }

    @Test
    void testUpdateShoppingList() {
        // Setup
        ShoppingList existing = new ShoppingList();
        existing.setIngredients(new ArrayList<>());

        ShoppingListDto dto = new ShoppingListDto();
        dto.setDate("2025-04-21");

        AppUserDto userDto = new AppUserDto();
        userDto.setEmail("test@example.com");
        dto.setUser(userDto);

        AppUser user = new AppUser();
        user.setEmail("test@example.com");

        MealPlanDto mealPlanDto = new MealPlanDto();
        mealPlanDto.setId(100L);
        dto.setMealPlan(mealPlanDto);

        MealPlan mealPlan = new MealPlan();
        mealPlan.setId(100L);

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(appUserRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(mealPlanRepo.findById(100L)).thenReturn(Optional.of(mealPlan));
        when(shoppingListRepository.save(existing)).thenReturn(existing);
        when(shoppingListMapper.toDto(existing)).thenReturn(dto);

        ShoppingListDto result = shoppingListService.updateShoppingList(1L, dto);

        assertEquals(dto.getDate(), result.getDate());
        verify(shoppingListRepository).save(existing);
        System.out.println("Update Result: " + result);
    }
    @Test
        void testDeleteShoppingList_NotFound() {
            when(shoppingListRepository.existsById(2L)).thenReturn(false);

            assertThrows(RuntimeException.class, () -> shoppingListService.deleteShoppingList(2L));
        }

}