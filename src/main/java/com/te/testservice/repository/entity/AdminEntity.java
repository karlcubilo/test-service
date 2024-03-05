package com.te.testservice.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Table(name = "admin", schema = "kc_util")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AdminEntity extends BaseEntity {

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
