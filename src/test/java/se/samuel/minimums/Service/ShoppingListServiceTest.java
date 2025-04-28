package se.samuel.minimums.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.AppUserMapper;
import se.samuel.minimums.Converter.IngredientsMapper;
import se.samuel.minimums.Converter.ShoppingListMapper;
import se.samuel.minimums.Dto.AppUserDto;
import se.samuel.minimums.Dto.ShoppingListDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Models.ShoppingList;
import se.samuel.minimums.Repo.ShoppingListRepo;

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
        private IngredientsMapper ingredientsMapper;

        @Mock
        private AppUserMapper appUserMapper;

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
            ShoppingList entity = new ShoppingList();

            when(shoppingListMapper.toEntity(dto)).thenReturn(entity);
            when(shoppingListRepository.save(entity)).thenReturn(entity);
            when(shoppingListMapper.toDto(entity)).thenReturn(dto);

            ShoppingListDto result = shoppingListService.createShoppingList(dto);

            assertEquals(dto, result);
            verify(shoppingListRepository).save(entity);
        }

        @Test
        void testUpdateShoppingList() {
            ShoppingList existing = new ShoppingList();
            existing.setIngredients(new ArrayList<>());

            ShoppingListDto dto = new ShoppingListDto();
            dto.setDate("2025-04-21");
            dto.setIngredients(new ArrayList<>());
            AppUserDto userDto = new AppUserDto();
            dto.setUser(userDto);

            AppUser user = new AppUser();

            when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(existing));
            when(appUserMapper.toEntity(userDto)).thenReturn(user);
            when(shoppingListRepository.save(existing)).thenReturn(existing);
            when(shoppingListMapper.toDto(existing)).thenReturn(dto);

            ShoppingListDto result = shoppingListService.updateShoppingList(1L, dto);

            assertEquals(dto.getDate(), result.getDate());
            verify(shoppingListRepository).save(existing);
            System.out.println("Update Result: " + result);
        }

        @Test
        void testDeleteShoppingList() {
            when(shoppingListRepository.existsById(1L)).thenReturn(true);

            shoppingListService.deleteShoppingList(1L);

            verify(shoppingListRepository).deleteById(1L);
        }

        @Test
        void testDeleteShoppingList_NotFound() {
            when(shoppingListRepository.existsById(2L)).thenReturn(false);

            assertThrows(RuntimeException.class, () -> shoppingListService.deleteShoppingList(2L));
        }

}