package com.gloddy.server.auth.security.config;

import com.gloddy.server.auth.jwt.JwtTokenExtractor;
import com.gloddy.server.auth.jwt.JwtTokenValidator;
import com.gloddy.server.auth.jwt.filter.JwtAuthenticationFilter;
import com.gloddy.server.auth.jwt.filter.JwtExceptionHandleFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenValidator jwtTokenValidator;
    private final AuthenticationProvider authenticationProvider;
    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.header}")
    private String secretHeader;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/*/**")
                .requestMatchers("/api/v1/search/**")
                .requestMatchers(HttpMethod.POST, "/api/v1/files/**")
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/*/**")
                .requestMatchers("/health")
                .requestMatchers("/api/v1/users/duplicate")
                .requestMatchers("/api/internal/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(it -> it.anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenExtractor,
                        jwtTokenValidator,
                        authenticationProvider,
                        secretHeader,
                        key), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionHandleFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
