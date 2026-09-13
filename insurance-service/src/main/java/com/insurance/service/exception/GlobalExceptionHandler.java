package com.insurance.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(final BusinessException exception) {
        final HttpStatus httpStatus = exception.getHttpStatus();
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            httpStatus,
            exception.getMessage()
        );
        problemDetail.setTitle("Business Error");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity
            .status(httpStatus)
            .body(problemDetail);
    }
}
