package com.te.testservice.common;

import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.admin.exception.AdminExceptionMessage;
import com.te.testservice.base.BaseUnitTest;
import com.te.testservice.common.errorhandling.ErrorHandlingAdvice;
import com.te.testservice.common.model.ErrorResponse;
import com.te.testservice.common.model.ValidationErrors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorHandlingAdviceTest extends BaseUnitTest {

    @InjectMocks
    ErrorHandlingAdvice errorHandlingAdvice;

    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    BindingResult bindingResult;

    @Test
    public void handleMethodArgumentNotValidException_whenArgumentNotValid_returnValidationErrors() {

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("test","method","message")));

        ValidationErrors validationErrors = errorHandlingAdvice.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        assertThat(validationErrors.getValidationErrors().get(0).getField(), equalTo("method"));
        assertThat(validationErrors.getValidationErrors().get(0).getMessage(), equalTo("message"));
    }

    @Test
    public void handleAdminException_whenAdminExceptionOccurred_returnErrorResponse(){

        ErrorResponse errorResponse = errorHandlingAdvice.handleAdminException(new AdminException(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode(), AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage()));

        assertThat(errorResponse.getErrors().get(0).getCode(), equalTo(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getCode()));
        assertThat(errorResponse.getErrors().get(0).getMessage(), equalTo(AdminExceptionMessage.ADMIN_ALREADY_EXIST.getMessage()));
    }

}
