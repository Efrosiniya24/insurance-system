package com.insurance.registry.testsupport;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@SpringBootTest
@Import(PostgresTestcontainersConfig.class)
public abstract class AbstractRegistryTest {
}
