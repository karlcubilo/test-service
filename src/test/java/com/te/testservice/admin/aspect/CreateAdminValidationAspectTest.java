package com.te.testservice.admin.aspect;

import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.admin.exception.AdminExceptionMessage;
import com.te.testservice.admin.model.Admin;
import com.te.testservice.base.BaseUnitTest;
import com.te.testservice.repository.dao.AdminRepository;
import com.te.testservice.repository.entity.AdminEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAdminValidationAspectTest extends BaseUnitTest {

    @InjectMocks
    CreateAdminValidationAspect createAdminValidationAspect;

    @Mock
    AdminRepository adminRepository;

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Captor
    ArgumentCaptor<String> lastNameArgumentCaptor;

    @Captor
    ArgumentCaptor<String> firstNameArgumentCaptor;


    @Test
    public void validate_whenAdminAlreadyExist_throwException()   {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLastName("last");
        adminEntity.setFirstName("first");

        when(adminRepository.findByLastNameAndFirstName(lastNameArgumentCaptor.capture(), firstNameArgumentCaptor.capture())).thenReturn(adminEntity);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{Admin.builder().lastName("last").firstName("first").build()});
        AdminException throwable = catchThrowableOfType(() -> createAdminValidationAspect.validate(proceedingJoinPoint), AdminException.class);

        assertThat(throwable.getCode(), equalTo(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode()));
        assertThat(throwable.getMessage(), equalTo(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage()));
        assertThat(lastNameArgumentCaptor.getValue(), equalTo("last"));
        assertThat(firstNameArgumentCaptor.getValue(), equalTo("first"));
    }

    @Test
    public void validate_whenAdminAlreadyExistButNotSameLastName_throwException() throws Throwable {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLastName("last");
        adminEntity.setFirstName("first");

        when(adminRepository.findByLastNameAndFirstName(lastNameArgumentCaptor.capture(), firstNameArgumentCaptor.capture())).thenReturn(adminEntity);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{Admin.builder().lastName("last1").firstName("first").build()});
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());
        Object object = createAdminValidationAspect.validate(proceedingJoinPoint);


        assertNotNull(object);
        assertThat(lastNameArgumentCaptor.getValue(), equalTo("last1"));
        assertThat(firstNameArgumentCaptor.getValue(), equalTo("first"));
    }

    @Test
    public void validate_whenAdminAlreadyExistButNotSameLastFirstName_throwException() throws Throwable {

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLastName("last");
        adminEntity.setFirstName("first");

        when(adminRepository.findByLastNameAndFirstName(lastNameArgumentCaptor.capture(), firstNameArgumentCaptor.capture())).thenReturn(adminEntity);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{Admin.builder().lastName("last").firstName("first1").build()});
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());
        Object object = createAdminValidationAspect.validate(proceedingJoinPoint);


        assertNotNull(object);
        assertThat(lastNameArgumentCaptor.getValue(), equalTo("last"));
        assertThat(firstNameArgumentCaptor.getValue(), equalTo("first1"));
    }


    @Test
    public void validate_whenAdminDoesNotExist_proceed() throws Throwable {

        when(adminRepository.findByLastNameAndFirstName(lastNameArgumentCaptor.capture(), firstNameArgumentCaptor.capture())).thenReturn(null);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{Admin.builder().lastName("last").firstName("first").build()});
        when(proceedingJoinPoint.proceed()).thenReturn(new Object());
        Object object = createAdminValidationAspect.validate(proceedingJoinPoint);

        assertThat(lastNameArgumentCaptor.getValue(), equalTo("last"));
        assertThat(firstNameArgumentCaptor.getValue(), equalTo("first"));
        assertNotNull(object);
    }



}
