package se.samuel.minimums.Convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.samuel.minimums.Dto.AppUserDto;
import se.samuel.minimums.Models.AppUser;


@Component
public class AppUserMapper {

    @Autowired
    private ModelMapper modelMapper;


    public AppUserDto AppUserToAppUserDto(AppUser appUser) {
        return modelMapper.map(appUser, AppUserDto.class);
    }

    public AppUser AppUserDtoToAppUser(AppUserDto appUserDto) {
        return modelMapper.map(appUserDto, AppUser.class);
    }
}
