package com.insurance.service.service;

import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.security.dto.AuthenticatedUserDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
public interface ContractService {

    /**
     * Issues a contract for an approved application.
     * Repeated calls return the existing contract
     *
     * @param applicationId  the id of the application on which the contract issuance is based
     * @param issuedByUserId the id of the user who issues the contract
     * @return the issued contract data
     */
    ContractDto issueContract(Long applicationId, String issuedByUserId);

    /**
     * Returns a contract if the caller is an underwriter or the owner of its application.
     *
     * @param contractId  contract id
     * @param currentUser authenticated user
     * @return contract data
     */
    ContractDto getContract(Long contractId, AuthenticatedUserDto currentUser);

    /**
     * Returns own contracts for a policyholder or all contracts for an underwriter
     *
     * @param currentUser authenticated user
     * @return contracts data
     */
    List<ContractDto> getContractList(AuthenticatedUserDto currentUser);
}
