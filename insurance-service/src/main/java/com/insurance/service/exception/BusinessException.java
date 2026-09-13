package com.insurance.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BusinessException(final String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
