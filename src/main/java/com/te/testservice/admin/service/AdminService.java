package com.te.testservice.admin.service;

import com.te.testservice.admin.aspect.CreateAdminValidation;
import com.te.testservice.admin.builder.AdminBuilder;
import com.te.testservice.admin.model.Admin;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.entity.AdminEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Admin createAdmin(Admin admin) {
        AdminEntity adminEntity =adminRepository.save(adminBuilder.build(admin));
        admin.setId(adminEntity.getId());
        return admin;
    }
}
