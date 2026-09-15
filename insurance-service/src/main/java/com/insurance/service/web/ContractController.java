package com.insurance.service.web;

import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.security.annotation.CurrentUser;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contract")
public class ContractController {
    private final ContractService contractService;

    @PreAuthorize("hasAuthority('UNDERWRITER')")
    @PostMapping("/{applicationId}/issue")
    public ResponseEntity<ContractDto> issueContract(
        final @PathVariable Long applicationId,
        final @CurrentUser AuthenticatedUserDto currentUser
    ) {
        final ContractDto contract = contractService.issueContract(applicationId, currentUser.getId());
        return ResponseEntity
            .status(contract.isAlreadyIssued() ? HttpStatus.OK : HttpStatus.CREATED)
            .body(contract);
    }

    @PreAuthorize("hasAnyAuthority('POLICYHOLDER', 'UNDERWRITER')")
    @GetMapping("/{id}")
    public ResponseEntity<ContractDto> getContract(
        final @PathVariable Long id,
        final @CurrentUser AuthenticatedUserDto currentUser
    ) {
        return ResponseEntity.ok(contractService.getContract(id, currentUser));
    }

    @PreAuthorize("hasAnyAuthority('POLICYHOLDER', 'UNDERWRITER')")
    @GetMapping("/all")
    public ResponseEntity<Page<ContractDto>> getContracts(
        final @CurrentUser AuthenticatedUserDto currentUser,
        final @PageableDefault(sort = "createdAt", size = 20, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(contractService.getContractList(currentUser, pageable));
    }
}
