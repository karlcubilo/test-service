package com.te.testservice.repository.dao;

import com.te.testservice.config.FakeDataConfig;
import com.te.testservice.repository.dto.AdminDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("mock")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {FakeDataConfig.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @Test
    public void findByLastNameAndFirstName_whenDataExist_returnAdminDto() {

        AdminDto adminDto = adminRepository.findByLastNameAndFirstName("CUBILO", "KARL MARVIN");

        assertThat(adminDto.getLastName(), equalTo("CUBILO"));
        assertThat(adminDto.getFirstName(), equalTo("KARL MARVIN"));
        assertThat(adminDto.getAge(), equalTo(32));
    }
}
