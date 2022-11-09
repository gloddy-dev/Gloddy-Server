package com.gloddy.server.core.error.handler;

import com.gloddy.server.core.error.handler.exception.BaseBusinessException;
import com.gloddy.server.core.error.handler.exception.FileBusinessException;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.core.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.gloddy.server")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {
            UserBusinessException.class,
            FileBusinessException.class
    })
    public ResponseEntity<ErrorResponse> handleBusinessException(BaseBusinessException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getErrorCode()));
    }
}
