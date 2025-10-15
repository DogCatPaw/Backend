package kpaas.dogcat.global.config;

import kpaas.dogcat.global.jwt.CustomUserDetailsService;
import kpaas.dogcat.global.jwt.JwtFilter;
import kpaas.dogcat.global.jwt.JwtUtil;
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

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private String[] allowUrl = {
            "/api/auth/signup",
            "/api/auth/login",

            "/ws/**",

            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",

            "/api/story/daily/{storyId}",
            "/api/story/daily/stories",
            "/api/story/daily/search",

            "/api/story/review/{reviewId}",
            "/api/story/review/reviews",
            "/api/story/review/search",
            "/api/comment/",

            "/api/donation/mine",
            "/api/donation/list",
            "/api/donation/bone",
            "/api/donations/{donationId}",

            "/api/adoption/home",
            "/api/adoption/",
            "/api/adoption/mine",
            "/api/adoption/detail/{adoptId}"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrl).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)  //jwt이기에 csrf공격 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());
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
