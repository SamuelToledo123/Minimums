package se.samuel.minimums.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.samuel.minimums.Convert.AppUserMapper;
import se.samuel.minimums.Dto.AppUserDto;
import se.samuel.minimums.Models.AppUser;
import se.samuel.minimums.Repo.AppUserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepo repo;
    @Autowired
    private AppUserMapper appUserMapper;

    public List<AppUserDto> getAllUsers() {
        return repo.findAll().stream()
                .map(appUserMapper::AppUserToAppUserDto)
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

        AppUser newUser = appUserMapper.AppUserDtoToAppUser(appUserDto);
        AppUser savedUser = repo.save(newUser);

        return appUserMapper.AppUserToAppUserDto(savedUser);
    }
    public AppUserDto updateAppUser(Long id, AppUserDto appUserDto) {
        AppUser existingUser = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(appUserDto.getName());
        existingUser.setEmail(appUserDto.getEmail());
        existingUser.setPassword(appUserDto.getPassword());

        AppUser updatedUser = repo.save(existingUser);
        return appUserMapper.AppUserToAppUserDto(updatedUser);
    }

    public String deleteUser(Long id) {
        AppUser user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        repo.delete(user);
        return "User with id " + id + " has been deleted.";
    }
}