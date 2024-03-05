package com.te.testservice.dao.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AdminDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "ADMIN_ID")
    Long id;

    @Column(name  = "LAST_NAME")
    String lastName;

    @Column(name  = "FIRST_NAME")
    String firstName;

    @Column(name  = "AGE")
    Integer age;
}
