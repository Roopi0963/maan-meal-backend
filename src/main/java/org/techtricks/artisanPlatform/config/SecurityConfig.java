package org.techtricks.artisanPlatform.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/auth/login**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/product/update/**").permitAll()  // ✅ Allow product updates
                                .requestMatchers("/api/user/**").permitAll()
                                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/product/products").permitAll()  // Allow fetching products

                                // Restricted Endpoints (Role-Based)
                                .requestMatchers(HttpMethod.PUT, "/api/product/update/**").permitAll() // Only Admins can update products
                                .requestMatchers(HttpMethod.POST, "/api/product/create").permitAll()  // Only Admins can create products
                                .requestMatchers("/api/admin/**").permitAll()    // Protect admin APIs
                                .requestMatchers("/api/artisan/**").permitAll()  // Protect Artisan APIs
                                .requestMatchers("/api/buyer/**").permitAll()    // Protect Buyer APIs
// ✅ Allow product list retrieval
                        .anyRequest().permitAll()
                );
        return http.build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // ✅ React frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
