package com.te.testservice.admin.controller;

import com.te.testservice.admin.model.Admin;
import com.te.testservice.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Validated
public class AdminController {

    final AdminService adminService;

    @GetMapping
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody @Valid Admin admin) {
        return adminService.createAdmin(admin);
    }
}
