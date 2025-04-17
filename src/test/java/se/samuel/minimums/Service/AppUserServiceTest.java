package se.samuel.minimums.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import se.samuel.minimums.Converter.AppUserMapper;
import se.samuel.minimums.Dto.AppUserDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Repo.AppUserRepo;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepo repo;

    @Mock
    private AppUserMapper mapper;

    @InjectMocks
    private AppUserService service;

    private AppUser user;
    private AppUserDto dto;

    @BeforeEach
    void setup() {
         user = AppUser.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .build();

         dto = AppUserDto.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .build();
    }

    @Test
    void getAllUsers_returnsDtoList() {
        when(repo.findAll()).thenReturn(List.of(user));
        when(mapper.AppUserToAppUserDto(user)).thenReturn(dto);

        List<AppUserDto> result = service.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getName());
        verify(repo).findAll();
    }

    @Test
    void getUserByEmail_returnsUser() {
        when(repo.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<AppUser> result = service.getUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());
        verify(repo).findByEmail("test@example.com");
    }

    @Test
    void getUserById_returnsOptionalUser() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));

        Optional<AppUser> result = service.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());
        verify(repo).findById(1L);
    }

    @Test
    void createUser_success() {
        when(repo.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(mapper.AppUserDtoToAppUser(dto)).thenReturn(user);
        when(repo.save(user)).thenReturn(user);
        when(mapper.AppUserToAppUserDto(user)).thenReturn(dto);

        AppUserDto result = service.createUser(dto);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(repo).save(user);
    }

    @Test
    void createUser_throwsIfEmailExists() {
        when(repo.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        Exception ex = assertThrows(RuntimeException.class, () -> service.createUser(dto));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void updateAppUser_success() {
        AppUser updatedUser = AppUser.builder()
                .id(1L)
                .name("New Name")
                .email("new@email.com")
                .password("newpass")
                .build();

        AppUserDto updatedDto = AppUserDto.builder()
                .id(1L)
                .name("New Name")
                .email("new@email.com")
                .password("newpass")
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(user));
        when(repo.save(any(AppUser.class))).thenReturn(updatedUser);
        when(mapper.AppUserToAppUserDto(updatedUser)).thenReturn(updatedDto);

        AppUserDto result = service.updateAppUser(1L, updatedDto);

        assertEquals("New Name", result.getName());
        verify(repo).save(any(AppUser.class));
    }

    @Test
    void deleteUser_success() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));

        String result = service.deleteUser(1L);

        assertEquals("User with id 1 has been deleted.", result);
        verify(repo).delete(user);
    }

    @Test
    void deleteUser_throwsIfNotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> service.deleteUser(999L));
        assertTrue(ex.getMessage().contains("User not found"));
    }
}
