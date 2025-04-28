package se.samuel.minimums.Security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.samuel.minimums.Service.RegistrationService;
import se.samuel.minimums.Dto.RegistrationRequestDto;
import se.samuel.minimums.Dto.UserResponseDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Repo.AppUserRepo;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private AppUserRepo appUserRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testRegisterUserSuccess() {

        RegistrationRequestDto request = new RegistrationRequestDto();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");


        when(appUserRepo.findByEmail(request.getEmail())).thenReturn(Optional.empty());


        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        AppUser mockUser = AppUser.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("hashedPassword")
                .role("USER")
                .build();
        when(appUserRepo.save(any(AppUser.class))).thenReturn(mockUser);


        ResponseEntity<String> response = registrationService.registerUser(request);


        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User is successfully registered", response.getBody());

        verify(appUserRepo, times(1)).findByEmail(request.getEmail());
        verify(appUserRepo, times(1)).save(any(AppUser.class));
    }
    @Test
    public void testRegisterUserConflict() {
        RegistrationRequestDto request = new RegistrationRequestDto();
        request.setEmail("test@example.com");

        when(appUserRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(new AppUser()));

        ResponseEntity<String> response = registrationService.registerUser(request);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Email already in use", response.getBody());
    }

    @Test
    public void testGetUserDetailsAfterLogin() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");

        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setName("Test User");
        appUser.setEmail("test@example.com");
        appUser.setRole("USER");

        when(appUserRepo.findByEmail(authentication.getName())).thenReturn(Optional.of(appUser));

        ResponseEntity<?> response = registrationService.getUserDetailsAfterLogin(authentication.getName());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof UserResponseDto);
    }

    @Test
    public void testGetUserDetailsNotFound() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("nonexistent@example.com");

        when(appUserRepo.findByEmail(authentication.getName())).thenReturn(Optional.empty());

        ResponseEntity<?> response = registrationService.getUserDetailsAfterLogin(authentication.getName());

        assertEquals(401, response.getStatusCodeValue());
    }
}
