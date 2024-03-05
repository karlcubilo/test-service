package com.te.testservice.admin.service;

import com.te.testservice.admin.aspect.CreateAdminValidation;
import com.te.testservice.admin.builder.AdminBuilder;
import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.admin.exception.AdminExceptionMessage;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.entity.AdminEntity;
import com.te.testservice.admin.model.Admin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminService {

    final AdminRepository adminRepository;
    final AdminBuilder adminBuilder;

    public List<Admin> getAdmins() {

        List<AdminEntity> adminEntities = adminRepository.findAll();

        return adminEntities.stream().map(a -> Admin.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .age(a.getAge())
                .build()).collect(Collectors.toList());
    }

    @CreateAdminValidation
    public Admin createAdmin(Admin admin) {
        adminRepository.save(adminBuilder.build(admin));
        return admin;
    }


}
