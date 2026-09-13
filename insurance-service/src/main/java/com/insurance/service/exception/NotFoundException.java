package com.insurance.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
public class NotFoundException extends BusinessException {
    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
