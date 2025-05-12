package se.samuel.minimums.AppUser;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import se.samuel.minimums.AppUser.Dto.AppUserDto;


@Component
@RequiredArgsConstructor
public class AppUserMapper {

    private final ModelMapper modelMapper;

    public AppUserDto toDto(AppUser appUser) {
        return modelMapper.map(appUser, AppUserDto.class);
    }

    public AppUser toEntity(AppUserDto appUserDto) {
        return modelMapper.map(appUserDto, AppUser.class);
    }
}
