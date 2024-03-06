package com.te.testservice.admin.builder;

import com.te.testservice.admin.model.Admin;
import com.te.testservice.base.BaseUnitTest;
import com.te.testservice.repository.entity.AdminEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminBuilderTest extends BaseUnitTest {

    @InjectMocks
    AdminBuilder adminBuilder;

    @Test
    public void build_whenAdminProvided_returnAdminEntity() {

        AdminEntity adminEntity = adminBuilder.build(Admin.builder().age(12).firstName("Test").lastName("Cubilo").build());

        assertThat(adminEntity.getAge(), equalTo(12));
        assertThat(adminEntity.getLastName(), equalTo("Cubilo"));
        assertThat(adminEntity.getFirstName(), equalTo("Test"));
    }
}
