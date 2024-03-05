package com.te.testservice.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminExceptionMessage {

    ADMIN_ALREADY_EXIST("422","Admin already exist");

    final String code;
    final String message;
}
