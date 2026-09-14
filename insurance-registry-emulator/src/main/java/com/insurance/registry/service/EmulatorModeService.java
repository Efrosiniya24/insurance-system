package com.insurance.registry.service;

import com.insurance.registry.enums.EmulatorMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Service
public class EmulatorModeService {
    private final AtomicReference<EmulatorMode> mode = new AtomicReference<>(EmulatorMode.SUCCESS);

    public EmulatorMode getMode() {
        return mode.get();
    }

    public void setMode(final EmulatorMode mode) {
        this.mode.set(mode);
    }

    public void applyMode() {
        switch (mode.get()) {
            case UNAVAILABLE -> throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Registry unavailable");
            case BUSINESS_ERROR -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business error");
            case TECHNICAL_ERROR -> throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Technical error");
            case SUCCESS -> {
            }
        }
    }
}