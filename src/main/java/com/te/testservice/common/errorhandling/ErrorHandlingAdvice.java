package com.te.testservice.common.errorhandling;

import com.te.testservice.admin.exception.AdminException;
import com.te.testservice.common.model.Error;
import com.te.testservice.common.model.ErrorResponse;
import com.te.testservice.common.model.ValidationErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ErrorHandlingAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrors handleMethodArgumentNotValidException (MethodArgumentNotValidException methodArgumentNotValidException) {
        BindingResult result = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(AdminException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handleAdminException(AdminException adminException) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(Collections.singletonList(Error.builder().code(adminException.getCode()).message(adminException.getMessage()).build()));
        return  errorResponse;
    }


    private ValidationErrors processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrors validationErrors = new ValidationErrors();
        fieldErrors.stream().forEach(f -> validationErrors.addErrors(f.getField(), f.getDefaultMessage()));
        return validationErrors;
    }
}
