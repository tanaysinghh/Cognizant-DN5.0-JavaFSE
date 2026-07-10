package com.cognizant.springlearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * HOL 5: sanity check — ensure the full Spring context loads with security enabled.
 */
@SpringBootTest
class SpringLearnApplicationTest {

    @Test
    void contextLoads() {
        // If security wiring or any bean fails to load, this test fails
    }
}
