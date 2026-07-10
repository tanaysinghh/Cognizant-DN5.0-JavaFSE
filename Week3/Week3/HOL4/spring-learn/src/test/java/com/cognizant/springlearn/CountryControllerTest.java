package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import com.cognizant.springlearn.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HOL 4: MockMvc tests for the CountryController.
 * - happy path: GET /countries
 * - exceptional: POST with invalid body → GlobalExceptionHandler returns 400
 */
@WebMvcTest(CountryController.class)
@Import(GlobalExceptionHandler.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCountries() throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].name").value("United States"));
    }

    @Test
    void shouldRejectInvalidCountry() throws Exception {
        String badJson = "{\"code\":\"\",\"name\":\"\"}";
        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}
