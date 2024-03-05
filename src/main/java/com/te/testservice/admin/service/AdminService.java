package com.te.testservice.admin.service;

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

    public Admin createAdmin(Admin admin) {

        AdminEntity adminEntity = adminRepository.findByLastNameAndFirstName(admin.getLastName(), admin.getFirstName());
        if(!isValidAdmin(admin, adminEntity)) {
            throw new AdminException(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode(), AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage());
        }
        adminRepository.save(adminBuilder.build(admin));
        return admin;
    }

    private boolean isValidAdmin(Admin admin, AdminEntity adminEntity) {

        boolean isValid = true;
        if(Objects.nonNull(adminEntity) && admin.getLastName().equalsIgnoreCase(adminEntity.getLastName()) && admin.getFirstName().equalsIgnoreCase(adminEntity.getFirstName())) {
            isValid = false;
        }
        return isValid;
    }
}
