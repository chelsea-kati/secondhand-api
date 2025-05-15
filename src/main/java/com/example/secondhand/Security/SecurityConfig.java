package com.example.secondhand.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.secondhand.Service.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/annonces/approuvees").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/annonces/approuvees").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/annonces/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/commentaires/annonce/*").permitAll()

                        // routes UTILISATEUR (authentifié)
                        .requestMatchers(HttpMethod.POST, "/api/annonces/**").hasRole("UTILISATEUR")
                        .requestMatchers(HttpMethod.PUT, "/api/annonces/*").hasRole("UTILISATEUR")
                        //.requestMatchers(HttpMethod.DELETE, "/api/annonces/*").hasRole("UTILISATEUR")
                        .requestMatchers("/api/favoris/**").hasRole("UTILISATEUR")
                        .requestMatchers(HttpMethod.POST, "/api/commentaires/**").hasRole("UTILISATEUR")
                        .requestMatchers(HttpMethod.DELETE, "/api/commentaires/*").hasRole("UTILISATEUR")

                        // routes ADMIN (authentifié)
                        .requestMatchers(HttpMethod.DELETE, "/api/annonces/*").hasAnyRole("UTILISATEUR", "ADMIN")

                        //.requestMatchers(HttpMethod.PUT, "/api/annonces/*/approuver").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/annonces/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/favoris/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/commentaires/**").hasRole("ADMIN")

                        // Toutes les autres requêtes nécessitent authentification
                        //.anyRequest().authenticated())
                        .anyRequest().permitAll())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
