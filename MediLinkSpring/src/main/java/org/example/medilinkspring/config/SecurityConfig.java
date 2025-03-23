package org.example.medilinkspring.config;

import lombok.RequiredArgsConstructor;
import org.example.medilinkspring.config.security.jwt.JwtAuthenticationFilter;
import org.example.medilinkspring.config.security.jwt.JwtAuthorizationFilter;
import org.example.medilinkspring.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    private final CorsConfig corsConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {


        return http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (JWT 사용 시 필요)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함
                .addFilter(corsConfig.corsFilter()) //필터 적용


                .formLogin(form -> form.disable()) // 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // HTTP Basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/user/**").hasAnyRole("USER","MANAGER", "ADMIN")
//                                .requestMatchers("/api/hos/**").hasAnyRole("USER","MANAGER", "ADMIN")
                                .requestMatchers("/public/**", "/login", "/signup").permitAll()
                                .anyRequest().permitAll() // 그 외 경로는 인증 없이 접근
                )
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration),userRepository),JwtAuthenticationFilter.class)
                .build();




    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


}
