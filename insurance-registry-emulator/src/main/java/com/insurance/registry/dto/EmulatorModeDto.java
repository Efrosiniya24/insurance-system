package com.insurance.registry.dto;

import com.insurance.registry.enums.EmulatorMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmulatorModeDto {
    private EmulatorMode mode;
}