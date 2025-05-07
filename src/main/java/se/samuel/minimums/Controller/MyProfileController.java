package se.samuel.minimums.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.Dto.MyProfileDto;
import se.samuel.minimums.Service.MyProfileService;

@RestController
@RequestMapping("/api/my-profile")
@RequiredArgsConstructor
public class MyProfileController {

    private final MyProfileService myProfileService;

    @GetMapping
    public ResponseEntity<MyProfileDto> getMyProfile(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(401).build();
        }

        MyProfileDto profile = myProfileService.getProfile(auth.getName());
        return ResponseEntity.ok(profile);
    }
}