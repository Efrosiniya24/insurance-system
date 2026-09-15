package com.insurance.service.testsupport;

import com.insurance.service.client.registry.RegistryClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@SpringBootTest(properties = "spring.task.scheduling.enabled=false")
@Import({ PostgresTestcontainersConfig.class, ApplicationTestData.class })
public abstract class AbstractInsuranceServiceTest {

    @MockitoBean
    protected JwtDecoder jwtDecoder;

    @MockitoBean
    protected RegistryClient registryClient;
}
