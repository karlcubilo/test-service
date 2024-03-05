package com.te.testservice.admin.builder;

import com.te.testservice.admin.model.Admin;
import com.te.testservice.repository.entity.AdminEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminBuilder {

    public AdminEntity build(Admin admin) {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAge(admin.getAge());
        adminEntity.setFirstName(admin.getFirstName());
        adminEntity.setLastName(admin.getLastName());

        return adminEntity;
    }
}
