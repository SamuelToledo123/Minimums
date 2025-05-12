package se.samuel.minimums.Child;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.AppUser.Service.AppUserService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepo childRepo;
    private final ChildMapper childMapper;
    private final AppUserService appUserService;

    public List<ChildDto> getAllChildren() {
        return childRepo.findAll()
                .stream()
                .map(childMapper::toDto).toList();

    }

    public ChildDto createChild(ChildDto childDto, String userEmail) {
        AppUser user = appUserService.getUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Child newChild = childMapper.toEntity(childDto);
        newChild.setUser(user);

        Child savedChild = childRepo.save(newChild);

        return childMapper.toDto(savedChild);
    }


    public Optional<Child> getChildById(Long id) {
       return childRepo.findById(id);
    }

    public ChildDto updateChild(Long id, ChildDto updatedDto) {
        Child child = childRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        child.setName(updatedDto.getName());
        child.setAge(updatedDto.getAge());
        child.setAllergies(updatedDto.getAllergies());
        Child savedChild = childRepo.save(child);
        return childMapper.toDto(savedChild);
    }

    public String deleteChild(Long id) {
        Child child = childRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found"));

        AppUser user = child.getUser();
        user.getChildren().remove(child);

        childRepo.delete(child);

        return "Child deleted successfully.";
    }
}










