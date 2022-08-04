package com.gloddy.server.auth.jwt.filter;

import com.gloddy.server.auth.jwt.JwtAuthentication;
import com.gloddy.server.auth.jwt.JwtTokenExtractor;
import com.gloddy.server.auth.jwt.JwtTokenValidator;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenValidator jwtTokenValidator;
    private final AuthenticationProvider authenticationProvider;
    private final String TOKEN_HEADER;
    private final String KEY;

    public JwtAuthenticationFilter(JwtTokenExtractor jwtTokenExtractor,
                                   JwtTokenValidator jwtTokenValidator,
                                   AuthenticationProvider authenticationProvider,
                                   @Value("${jwt.header}") String secretHeader,
                                   @Value("${jwt.secret}") String key) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.jwtTokenValidator = jwtTokenValidator;
        this.authenticationProvider = authenticationProvider;
        this.TOKEN_HEADER = secretHeader;
        this.KEY = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = jwtTokenExtractor.extractToken(request, TOKEN_HEADER);
            if (jwtTokenValidator.validateToken(token)) {
                throw new JwtException("exception");
            }

            String email = jwtTokenExtractor.extractEmailFromToken(token, KEY);
            Authentication authentication = JwtAuthentication.of(null, email);

            Authentication authenticated = authenticationProvider.authenticate(authentication);

            SecurityContextHolder.getContext()
                    .setAuthentication(authenticated);

            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new UserBusinessException(ErrorCode.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            throw new UserBusinessException(ErrorCode.TOKEN_EXPIRED);
        }

    }
}
