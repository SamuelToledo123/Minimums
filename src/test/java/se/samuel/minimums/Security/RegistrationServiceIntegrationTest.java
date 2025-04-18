package se.samuel.minimums.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import se.samuel.minimums.Dto.RegistrationRequestDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Repo.AppUserRepo;
import se.samuel.minimums.Service.RegistrationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrationServiceIntegrationTest {

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        appUserRepo.deleteAll();
    }

    @Test
    public void testRegisterUserSuccess() {

        RegistrationRequestDto request = new RegistrationRequestDto();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        ResponseEntity<String> response = registrationService.registerUser(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User is successfully registered", response.getBody());

        AppUser savedUser = appUserRepo.findByEmail("test@example.com").orElse(null);
        assertNotNull(savedUser);
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword()));  // Verify password is hashed
    }

    @Test
    public void testRegisterUserConflict() {
        RegistrationRequestDto request = new RegistrationRequestDto();
        request.setEmail("test@example.com");

        AppUser existingUser = new AppUser();
        existingUser.setEmail("test@example.com");
        existingUser.setName("Existing User");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        appUserRepo.save(existingUser);

        ResponseEntity<String> response = registrationService.registerUser(request);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("Email already in use", response.getBody());
    }
}
