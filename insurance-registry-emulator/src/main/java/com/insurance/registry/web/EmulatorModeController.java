package com.insurance.registry.web;

import com.insurance.registry.dto.EmulatorModeDto;
import com.insurance.registry.service.EmulatorModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mode")
public class EmulatorModeController {
    private final EmulatorModeService emulatorModeService;

    @GetMapping
    public ResponseEntity<EmulatorModeDto> getMode() {
        return ResponseEntity.ok(new EmulatorModeDto(emulatorModeService.getMode()));
    }

    @PutMapping
    public ResponseEntity<EmulatorModeDto> setMode(final @RequestBody EmulatorModeDto request) {
        emulatorModeService.setMode(request.getMode());
        return ResponseEntity.ok(new EmulatorModeDto(emulatorModeService.getMode()));
    }
}