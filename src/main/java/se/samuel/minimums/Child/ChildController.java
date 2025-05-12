package se.samuel.minimums.Child;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.samuel.minimums.AppUser.AppUserRepo;

import java.util.List;

@RestController
@RequestMapping("/api/child")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;
    private final AppUserRepo userRepository;

    @GetMapping
    public ResponseEntity<List<ChildDto>> getAllChildren() {
        return ResponseEntity.ok(childService.getAllChildren());
    }

    @PostMapping
    public ResponseEntity<ChildDto> createChild(@RequestBody ChildDto childDto, Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(childService.createChild(childDto, email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildDto> getChildById(@PathVariable Long id) {
        return childService.getChildById(id)
                .map(child -> ResponseEntity.ok().body(childService.getAllChildren()
                        .stream().filter(dto -> dto.getId().equals(id)).findFirst().orElse(null)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildDto> updateChild(@PathVariable Long id, @RequestBody ChildDto updatedDto) {
        return ResponseEntity.ok(childService.updateChild(id, updatedDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChild(@PathVariable Long id) {
        return ResponseEntity.ok(childService.deleteChild(id));
    }
}



