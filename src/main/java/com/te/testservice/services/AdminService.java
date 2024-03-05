package com.te.testservice.services;

import com.te.testservice.dao.AdminDAO;
import com.te.testservice.dao.dto.AdminDto;
import com.te.testservice.model.Admin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdminService {

    final AdminDAO adminDAO;

    public List<Admin> getAdmins() {

        List<AdminDto> adminDtos = adminDAO.findAll();


        return adminDtos.stream().map(a -> Admin.builder()
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .age(a.getAge())
                .build()).collect(Collectors.toList());
    }
}
