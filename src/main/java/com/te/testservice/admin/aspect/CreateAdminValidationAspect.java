package com.te.testservice.admin.aspect;

import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.admin.exception.AdminExceptionMessage;
import com.te.testservice.admin.model.Admin;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.entity.AdminEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class CreateAdminValidationAspect {

    final AdminRepository adminRepository;

    @Around("@annotation(CreateAdminValidation)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] arguments = joinPoint.getArgs();
        Admin admin = (Admin) arguments[0];
        AdminEntity adminEntity = adminRepository.findByLastNameAndFirstName(admin.getLastName(), admin.getFirstName());

        if (!isValidAdmin(admin, adminEntity)) {
            throw new AdminException(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode(), AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage());
        }

        return joinPoint.proceed();
    }

    private boolean isValidAdmin(Admin admin, AdminEntity adminEntity) {

        boolean isValid = true;
        if (Objects.nonNull(adminEntity) && (admin.getLastName().equalsIgnoreCase(adminEntity.getLastName()) && admin.getFirstName().equalsIgnoreCase(adminEntity.getFirstName()))) {
            isValid = false;
        }
        return isValid;
    }
}
