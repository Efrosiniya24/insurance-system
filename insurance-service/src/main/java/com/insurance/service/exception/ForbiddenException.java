package com.insurance.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super("Access denied", HttpStatus.FORBIDDEN);
    }
}
