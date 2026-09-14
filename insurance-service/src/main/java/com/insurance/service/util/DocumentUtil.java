package com.insurance.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Component
public class DocumentUtil {

    /**
     * Makes document number
     *
     * @param prefix document prefix
     * @return document number
     */
    public String makeDocumentNumber(final String prefix) {
        final int shortYear = LocalDate.now().getYear() % 100;
        final String uuid = UUID.randomUUID().toString()
            .replace("-", "")
            .substring(0, 8)
            .toUpperCase();
        return prefix + "-" + shortYear + uuid;
    }
}
