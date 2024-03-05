package com.te.testservice.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.te.testservice.admin.controller.AdminController;
import com.te.testservice.admin.model.Admin;
import com.te.testservice.admin.service.AdminService;
import com.te.testservice.base.BaseUnitTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminControllerTest extends BaseUnitTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    AdminService adminService;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Admin> adminArgumentCaptor;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAdmins_whenValidRequest_returnAdmins() throws Exception {

        Admin admin = new Admin();
        admin.setFirstName("test");
        admin.setLastName("lastName");
        admin.setAge(2);

        when(adminService.getAdmins()).thenReturn(List.of(admin));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Admin> admins = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Admin>>(){});

        assertThat(admins.size(), equalTo(1));
        assertThat(admins.get(0).getFirstName(), equalTo("test"));
        assertThat(admins.get(0).getLastName(), equalTo("lastName"));
        assertThat(admins.get(0).getAge(), equalTo(2));
    }

    @Test
    public void createAdmin_whenValidRequest_returnAdmin() throws Exception {

        when(adminService.createAdmin(adminArgumentCaptor.capture())).thenReturn(new Admin("test","test",3));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admins")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(new Admin("Cubilo","Karl",32)))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();


        Admin admin = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Admin.class);

        assertThat(admin.getAge(), equalTo(3));
        assertThat(admin.getFirstName(), equalTo("test"));
        assertThat(admin.getLastName(), equalTo("test"));
        assertThat(adminArgumentCaptor.getValue().getLastName(), equalTo("Cubilo"));
        assertThat(adminArgumentCaptor.getValue().getFirstName(), equalTo("Karl"));
        assertThat(adminArgumentCaptor.getValue().getAge(), equalTo(32));
    }

    @Test
    public void createAdmin_whenRequestInvalid_throwBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/admins")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(new Admin("","Karl",32)))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

    }




}
