package se.samuel.minimums.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.RegistrationRequestDto;
import se.samuel.minimums.Dto.UserResponseDto;
import se.samuel.minimums.Service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequestDto request) {
        return registrationService.registerUser(request);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<UserResponseDto> getUserDetailsAfterLogin(Authentication auth) {
        return registrationService.getUserDetailsAfterLogin(auth.getName());
    }
}
