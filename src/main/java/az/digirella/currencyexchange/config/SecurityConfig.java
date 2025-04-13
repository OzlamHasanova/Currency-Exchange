package az.digirella.currencyexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF hücumlarına qarşı qorumağı deaktiv edin (isteğe bağlı)
                .authorizeRequests()
                .requestMatchers("/api/exchange-rates/**").hasRole("USER") // Müəyyən API-lərə daxil olmaq üçün istifadəçi icazəsi
                .anyRequest().authenticated() // Qalan bütün tələblər üçün istifadəçi doğrulaması tələb edilir
                .and()
                .formLogin().disable() // Forma ilə login deaktivdir, yalnız token əsaslı autentifikasiya istifadə olunur
                .httpBasic().disable(); // Basic Auth deaktivdir, yalnız JWT istifadə olunur
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.builder()
                .username("user")
                .password(passwordEncoder().encode("password")) // Bu nöqtədə, istifadəçinin parolası şifrələnəcək
                .roles("USER")
                .build();
    }
}
