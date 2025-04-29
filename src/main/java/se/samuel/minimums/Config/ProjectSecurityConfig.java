package se.samuel.minimums.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration

public class ProjectSecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        /** httpSecurity.csrf(csrfConfig -> csrfConfig
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        httpSecurity.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class); */

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/register", "/api/auth/**", "api/recipes/**").permitAll()
                .requestMatchers("/", "/register", "/api/auth/**").permitAll()
                        .requestMatchers("/api/meal-plans/**").permitAll()
                        .requestMatchers("/user").authenticated()
                        .anyRequest().authenticated()


        );

        httpSecurity.formLogin(withDefaults())
                .oauth2Login(withDefaults());
        httpSecurity.httpBasic(withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}



