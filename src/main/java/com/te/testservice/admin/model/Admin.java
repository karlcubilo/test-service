package com.te.testservice.admin.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @NotBlank(message = "Last name is required")
    String lastName;
    String firstName;
    Integer age;
}
