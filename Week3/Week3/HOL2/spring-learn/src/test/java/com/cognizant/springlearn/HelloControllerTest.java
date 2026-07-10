package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HOL 2 — End-to-end test using MockMvc.
 * - @AutoConfigureMockMvc equivalent: @WebMvcTest sets up MockMvc for one controller.
 * - perform(get()) invokes the endpoint
 * - andExpect(status().isOk()) verifies HTTP 200
 * - andExpect(content().string(...)) verifies body text
 */
@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!!"));
    }
}
