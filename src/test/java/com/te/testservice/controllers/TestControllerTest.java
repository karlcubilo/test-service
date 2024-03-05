package com.te.testservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.testservice.base.BaseUnitTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestControllerTest extends BaseUnitTest {

    @InjectMocks
    TestController testController;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(testController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void test_whenRequestIsValid_returnHelloWorld() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tests"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, equalTo("Hello World"));
    }

}
