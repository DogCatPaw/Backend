package kpaas.dogcat.global.config;

import kpaas.dogcat.global.jwt.CustomUserDetailsService;
//import kpaas.dogcat.global.jwt.JwtFilter;
//import kpaas.dogcat.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity(debug = true)
public class SecurityConfig {

//    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private String[] allowUrl = {
            "/api/auth/signup",
            "/api/auth/login",

            "/ws/**",

            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",

            "/api/story/**",
            "/api/comment/**",
            "/api/like/**",
            "/api/donation/**",
            "/api/adoption/**",
            "/api/pet/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.addAllowedOriginPattern("*"); // Swagger / Front 모두 허용
                    config.addAllowedHeader("*");        // X-Wallet-Address 포함
                    config.addAllowedMethod("*");
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(allowUrl).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable);
//                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    JwtFilter jwtFilter() throws Exception {
//        return new JwtFilter(jwtUtil, customUserDetailsService);
//    }
}
