package com.example.demo12.config;

//import com.example.demo12.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour les API REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Active CORS avec la configuration personnalisée
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/admin/**").hasRole("ADMIN") // Seuls les ADMIN peuvent accéder
                        .requestMatchers("/api/users/client/**").hasRole("CLIENT") // Seuls les CLIENT peuvent accéder
                        .requestMatchers("/api/users/**").permitAll() // Autorise l'accès sans authentification
                        .requestMatchers("/api/twitte/**").permitAll() // Autorise l'accès sans authentification
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                );
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200")); // 🔥 Accepte Angular
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

//    @Bean
//    public JwtUtil jwtUtils() {
//        return new JwtUtil();
//    }
}
