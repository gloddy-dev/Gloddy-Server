package com.gloddy.server.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.core.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionHandleFilter extends OncePerRequestFilter {

    public JwtExceptionHandleFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            ErrorResponse errorResponse = createErrorResponse(exception);
            String errorJson = convertToJson(errorResponse);

            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorResponse.getStatus());
            response.getWriter().println(errorJson);
        }
    }

    private ErrorResponse createErrorResponse(Exception exception) {
        if (exception instanceof UserBusinessException userBusinessException) {
            return new ErrorResponse(userBusinessException.getErrorCode());
        } else {
            return ErrorResponse.of(
                    HttpStatus.UNAUTHORIZED.value(),
                    "UNAUTHORIZED",
                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
            );
        }
    }

    private String convertToJson(ErrorResponse errorResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorResponse);
    }
}
