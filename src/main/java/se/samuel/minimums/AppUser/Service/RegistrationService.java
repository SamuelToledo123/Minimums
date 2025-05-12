package se.samuel.minimums.AppUser.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.samuel.minimums.AppUser.Dto.RegistrationRequestDto;
import se.samuel.minimums.AppUser.Dto.UserResponseDto;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.AppUser.Authority;
import se.samuel.minimums.AppUser.AppUserRepo;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser(RegistrationRequestDto request) {
        try {
            if (appUserRepo.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }

            String hashedPassword = passwordEncoder.encode(request.getPassword());

            AppUser newUser = AppUser.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(hashedPassword)
                    .role("USER")
                    .build();

            Authority authority = new Authority();
            authority.setName("ROLE_USER");
            authority.setAppUser(newUser);
            newUser.setAuthorities(Set.of(authority));

            AppUser savedAppUser = appUserRepo.save(newUser);

            if (savedAppUser.getId() != null && savedAppUser.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User is successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred: " + ex.getMessage());
        }
    }


    public ResponseEntity<UserResponseDto> getUserDetailsAfterLogin(String email) {
        return appUserRepo.findByEmail(email)
                .map(user -> ResponseEntity.ok(new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole())))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
