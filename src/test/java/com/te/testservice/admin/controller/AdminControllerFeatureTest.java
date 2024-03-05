package com.te.testservice.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.testservice.admin.model.Admin;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mock")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminControllerFeatureTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getAdmins_whenRequestIsValid_returnAdmins() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Admin> admins = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Admin>>(){});

        assertFalse(admins.isEmpty());
        assertTrue(admins.stream().anyMatch(a-> a.getLastName().equalsIgnoreCase("Cubilo")));
        assertTrue(admins.stream().anyMatch(a-> a.getFirstName().equalsIgnoreCase("Karl Marvin")));
        assertTrue(admins.stream().anyMatch(a-> a.getAge() == 32));
    }

    @Test
    public void createAdmin_whenAdminIsValid_createAdmin() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admins")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(new Admin("Test123","Test",32,null)))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();


        Admin admin = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Admin.class);

        assertThat(admin.getAge(), equalTo(32));
        assertThat(admin.getFirstName(), equalTo("Test"));
        assertThat(admin.getLastName(), equalTo("Test123"));
        assertTrue(admin.getId()>1);
    }





}
