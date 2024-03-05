package com.te.testservice.common.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ValidationErrors {

    List<ValidationError> validationErrors = new ArrayList<>();
    public void addErrors(String field, String message) {
        validationErrors.add(ValidationError.builder().field(field).message(message).build());
    }
}
