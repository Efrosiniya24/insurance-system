package com.insurance.service.integration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.exception.BusinessException;
import com.insurance.service.service.ContractService;
import com.insurance.service.testsupport.AbstractInsuranceServiceTest;
import com.insurance.service.testsupport.ApplicationTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
class ContractIssueTest extends AbstractInsuranceServiceTest {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ApplicationTestData applicationTestData;

    @Test
    void issue_whenApplicationNotApproved_throwsBusinessException() {
        final Long applicationId = applicationTestData.createApplication(ApplicationStatus.PENDING);

        assertThatThrownBy(() -> contractService.issueContract(applicationId, "underwriter-test"))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("isn't approved");
    }
}
