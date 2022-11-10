package com.gloddy.server.auth.security.config;

import com.gloddy.server.auth.jwt.JwtTokenExtractor;
import com.gloddy.server.auth.jwt.JwtTokenValidator;
import com.gloddy.server.auth.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenValidator jwtTokenValidator;
    private final AuthenticationProvider authenticationProvider;
    @Value("${JWT_SECRET}")
    private String key;
    @Value("${JWT_HEADER}")
    private String secretHeader;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.POST,"/api/v1/auth/**")
                .antMatchers("/v2/api-docs",  "/configuration/ui",
                        "/swagger-resources", "/swagger-resources/*/**", "/configuration/security",
                        "/swagger-ui/*/**", "/webjars/**","/swagger/**");
    }

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
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenExtractor,
                        jwtTokenValidator,
                        authenticationProvider,
                        secretHeader,
                        key), UsernamePasswordAuthenticationFilter.class);
    }
}
