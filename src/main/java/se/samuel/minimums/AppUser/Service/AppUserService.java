package se.samuel.minimums.AppUser.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.AppUser.AppUserMapper;
import se.samuel.minimums.AppUser.AppUserRepo;
import se.samuel.minimums.AppUser.Dto.AppUserDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepo repo;
    private final AppUserMapper appUserMapper;

    public List<AppUserDto> getAllUsers() {
        return repo.findAll().stream()
                .map(appUserMapper::toDto)
                .toList();
    }

    public Optional<AppUser> getUserById(Long id) {
        return repo.findById(id);
    }
    public Optional<AppUser> getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public AppUserDto createUser(AppUserDto appUserDto) {
        if (repo.findByEmail(appUserDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + appUserDto.getEmail() + " already exists.");
        }

        AppUser newUser = appUserMapper.toEntity(appUserDto);
        AppUser savedUser = repo.save(newUser);

        return appUserMapper.toDto(savedUser);
    }
    public AppUserDto updateAppUser(Long id, AppUserDto appUserDto) {
        AppUser existingUser = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(appUserDto.getName());
        existingUser.setEmail(appUserDto.getEmail());

        AppUser updatedUser = repo.save(existingUser);
        return appUserMapper.toDto(updatedUser);
    }

    public String deleteUser(Long id) {
        AppUser user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        repo.delete(user);
        return "User with id " + id + " has been deleted.";
    }
}