package com.te.testservice.admin.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminException extends RuntimeException {

    String code;
    String message;

    public AdminException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
