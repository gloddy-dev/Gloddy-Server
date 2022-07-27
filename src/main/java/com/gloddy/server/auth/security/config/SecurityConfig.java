package com.gloddy.server.auth.security.config;

import com.gloddy.server.auth.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()
                .httpBasic()
                .disable()

                .cors()
                .and()

                .csrf()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers("/sign-up","/login").permitAll()
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
