package com.insurance.service.security.service.impl;

import com.insurance.service.exception.ForbiddenException;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.security.enums.UserRole;
import com.insurance.service.security.service.AccessService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@Service
public class AccessServiceImpl implements AccessService {
    @Override
    public void ownerUserOrUnderwriter(final AuthenticatedUserDto currentUser, final String createdByUserId) {
        if (currentUser.getRoles().contains(UserRole.UNDERWRITER)) {
            return;
        }
        if (Objects.equals(currentUser.getId(), createdByUserId)) {
            return;
        }
        throw new ForbiddenException();
    }
}
