package se.samuel.minimums.Security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.samuel.minimums.AppUser.AppUser;
import se.samuel.minimums.AppUser.AppUserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repo.findByEmail(username).orElseThrow(() -> new
                UsernameNotFoundException("User Details not found"));
        List<GrantedAuthority> authorities = appUser.getAuthorities().stream().map(authority -> new
                SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }
}

