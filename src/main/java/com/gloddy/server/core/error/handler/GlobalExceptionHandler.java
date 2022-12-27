package com.gloddy.server.core.error.handler;

import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
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

    private static final int INTERNAL_STATUS = 500;
    private static final String INTERNAL_MESSAGE = "SERVER_INTERNAL_ERROR";
    private static final String INTERNAL_REASON = "서버 내부 오류 입니다";

    @ExceptionHandler(value = {
            UserBusinessException.class,
            FileBusinessException.class,
            ArticleBusinessException.class
    })
    public ResponseEntity<ErrorResponse> handleBusinessException(BaseBusinessException e) {
        System.out.println("여기를 확인하세요");
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<ErrorResponse> handleInternalException(Exception e) {
        System.out.println("여기를 확인하세요");
        e.printStackTrace();
        return ResponseEntity.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(INTERNAL_STATUS, INTERNAL_MESSAGE, INTERNAL_REASON));
    }
}
