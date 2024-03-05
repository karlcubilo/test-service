package com.te.testservice.admin.service;

import com.te.testservice.admin.builder.AdminBuilder;
import com.te.testservice.admin.model.Admin;
import com.te.testservice.base.BaseUnitTest;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.entity.AdminEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminServiceTest extends BaseUnitTest {

    @InjectMocks
    AdminService adminService;

    @Mock
    AdminRepository adminRepository;

    @Mock
    AdminBuilder adminBuilder;

    @Captor
    ArgumentCaptor<Admin> adminArgumentCaptor;

    @Captor
    ArgumentCaptor<AdminEntity> adminEntityArgumentCaptor;


    @Test
    public void getAdmins_whenRequestIsValid_returnAdmins() {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAge(1);
        adminEntity.setFirstName("Karl");
        adminEntity.setLastName("Cubilo");

        when(adminRepository.findAll()).thenReturn(List.of(adminEntity));

        List<Admin> admins = adminService.getAdmins();

        assertThat(admins.size(), equalTo(1));
        assertThat(admins.get(0).getAge(), equalTo(1));
        assertThat(admins.get(0).getFirstName(), equalTo("Karl"));
        assertThat(admins.get(0).getLastName(), equalTo("Cubilo"));
    }

    @Test
    public void createAdmin_whenAdminIsValid_returnAdmin() {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLastName("adminEntity");

        AdminEntity adminEntityWithId = new AdminEntity();
        adminEntityWithId.setId(199L);

        Admin admin = new Admin("Cubilo","Karl",32,null);

        when(adminBuilder.build(adminArgumentCaptor.capture())).thenReturn(adminEntity);
        when(adminRepository.save(adminEntityArgumentCaptor.capture())).thenReturn(adminEntityWithId);

        Admin adminTest = adminService.createAdmin(admin);

        assertThat(adminTest.getId(), equalTo(199L));
        assertThat(adminTest.getFirstName(), equalTo("Karl"));
        assertThat(adminTest.getLastName(), equalTo("Cubilo"));
        assertThat(adminTest.getAge(), equalTo(32));
        assertThat(adminArgumentCaptor.getValue().getAge(), equalTo(32));
        assertThat(adminArgumentCaptor.getValue().getLastName(), equalTo("Cubilo"));
        assertThat(adminArgumentCaptor.getValue().getFirstName(), equalTo("Karl"));
        assertThat(adminEntityArgumentCaptor.getValue().getLastName(), equalTo("adminEntity"));

    }
}
