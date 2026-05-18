package se.jensen.linkan.userorderservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import se.jensen.linkan.userorderservice.security.JwtFilter;
import se.jensen.linkan.userorderservice.security.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/products/**", "/test-products").permitAll()
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter(),
                        org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class
                )

                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        System.out.println("🔥 CREATING JWT FILTER BEAN");
        return new JwtFilter(jwtUtil);
    }

    @PostConstruct
    public void debugSecurity() {
        System.out.println("🔥 SECURITY CONFIG LOADED");
    }
}