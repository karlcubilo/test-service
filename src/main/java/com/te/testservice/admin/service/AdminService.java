package com.te.testservice.admin.service;

import com.te.testservice.admin.builder.AdminBuilder;
import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.admin.exception.AdminExceptionMessage;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.dto.AdminDto;
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

        List<AdminDto> adminDtos = adminRepository.findAll();

        return adminDtos.stream().map(a -> Admin.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .age(a.getAge())
                .build()).collect(Collectors.toList());
    }

    public Admin createAdmin(Admin admin) {

        AdminDto adminDto = adminRepository.findByLastNameAndFirstName(admin.getLastName(), admin.getFirstName());
        if(!isValidAdmin(admin, adminDto)) {
            throw new AdminException(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode(), AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage());
        }
        adminRepository.save(adminBuilder.build(admin));
        return admin;
    }

    private boolean isValidAdmin(Admin admin, AdminDto adminDto) {

        boolean isValid = true;
        if(Objects.nonNull(adminDto) && admin.getLastName().equalsIgnoreCase(adminDto.getLastName()) && admin.getFirstName().equalsIgnoreCase(adminDto.getFirstName())) {
            isValid = false;
        }
        return isValid;
    }
}
