package com.te.testservice.admin.builder;

import com.te.testservice.admin.model.Admin;
import com.te.testservice.repository.dto.AdminDto;
import org.springframework.stereotype.Component;

@Component
public class AdminBuilder {

    public AdminDto build(Admin admin) {

        AdminDto adminDto = new AdminDto();
        adminDto.setAge(admin.getAge());
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setLastName(admin.getLastName());

        return  adminDto;
    }
}
