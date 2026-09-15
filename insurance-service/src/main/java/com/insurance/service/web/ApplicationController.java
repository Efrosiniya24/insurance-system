package com.insurance.service.web;

import com.insurance.service.model.application.dto.ApplicationDto;
import com.insurance.service.model.application.dto.ApplicationStatusResponseDto;
import com.insurance.service.model.application.dto.ApplicationUpdateStatusDto;
import com.insurance.service.model.application.dto.CreateApplicationRequestDto;
import com.insurance.service.security.annotation.CurrentUser;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("hasAnyAuthority('POLICYHOLDER', 'UNDERWRITER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplication(
        final @PathVariable Long id,
        final @CurrentUser AuthenticatedUserDto currentUser
    ) {
        return ResponseEntity.ok(applicationService.getApplication(id, currentUser));
    }

    @PreAuthorize("hasAnyAuthority('POLICYHOLDER', 'UNDERWRITER')")
    @GetMapping("/all")
    public ResponseEntity<Page<ApplicationDto>> getApplications(
        final @CurrentUser AuthenticatedUserDto currentUser,
        final @PageableDefault(sort = "createdAt", size = 20, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(applicationService.getApplicationList(currentUser, pageable));
    }

    @PreAuthorize("hasAuthority('POLICYHOLDER')")
    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(
        final @Valid @RequestBody CreateApplicationRequestDto request,
        final @CurrentUser AuthenticatedUserDto currentUser
    ) {
        return ResponseEntity.ok(applicationService.createApplication(request, currentUser.getId()));
    }

    @PreAuthorize("hasAuthority('UNDERWRITER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationStatusResponseDto> updateApplicationStatus(
        final @PathVariable Long id,
        final @Valid @RequestBody ApplicationUpdateStatusDto applicationUpdateStatusDto
    ) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, applicationUpdateStatusDto));
    }
}
