package com.insurance.service.integration;

import static org.assertj.core.api.Assertions.assertThat;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.contract.repository.ContractRepository;
import com.insurance.service.service.ContractService;
import com.insurance.service.testsupport.AbstractInsuranceServiceTest;
import com.insurance.service.testsupport.ApplicationTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
class ConcurrentContractIssueTest extends AbstractInsuranceServiceTest {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ApplicationTestData applicationTestData;

    @Test
    void twoParallelIssues_createOnlyOneContract() throws Exception {
        final Long applicationId = applicationTestData.createApprovedApplication();

        final CountDownLatch readyLatch = new CountDownLatch(2);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(2);
        final ExecutorService pool = Executors.newFixedThreadPool(2);
        final List<ContractDto> results = Collections.synchronizedList(new ArrayList<>());
        final List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

        final Runnable issueTask = () -> {
            try {
                readyLatch.countDown();
                start.await();
                results.add(contractService.issueContract(applicationId, "underwriter-test"));
            } catch (final Throwable ex) {
                errors.add(ex);
            } finally {
                done.countDown();
            }
        };
        pool.submit(issueTask);
        pool.submit(issueTask);

        readyLatch.await();
        start.countDown();
        final boolean finished = done.await(30, TimeUnit.SECONDS);
        pool.shutdownNow();

        assertThat(finished).isTrue();
        assertThat(errors).isEmpty();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(results.get(1).getId());
        assertThat(results.get(0).getContractNumber()).isEqualTo(results.get(1).getContractNumber());
        assertThat(results).extracting(ContractDto::isAlreadyIssued).containsExactlyInAnyOrder(true, false);
        assertThat(contractRepository.findByApplicationId(applicationId)).isPresent();
        assertThat(results).extracting(ContractDto::getApplicationId).containsOnly(applicationId);
    }

    @Test
    void repeatIssue_returnsExistingContract() {
        final Long applicationId = applicationTestData.createApprovedApplication();

        final ContractDto first = contractService.issueContract(applicationId, "underwriter-1");
        final ContractDto second = contractService.issueContract(applicationId, "underwriter-2");

        assertThat(first.isAlreadyIssued()).isFalse();
        assertThat(second.isAlreadyIssued()).isTrue();
        assertThat(second.getId()).isEqualTo(first.getId());
        assertThat(second.isIssuedByAnotherUser()).isTrue();
        assertThat(contractRepository.findByApplicationId(applicationId)).isPresent();
    }
}
