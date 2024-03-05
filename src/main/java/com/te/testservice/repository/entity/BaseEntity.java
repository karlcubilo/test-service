package com.te.testservice.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity implements Serializable {

    @Column(name  = "CREATED_DATE")
    LocalDateTime createdDate;

    @Column(name  = "UPDATED_DATE")
    LocalDateTime updatedDate;

    @Column(name  = "CREATED_BY")
    String createdBy;

    @Column(name  = "UPDATED_BY")
    String updatedBy;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedDate(LocalDateTime.now());
        this.setUpdatedDate(LocalDateTime.now());
        this.setCreatedBy(createdBy);
        this.setUpdatedBy(updatedBy);
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedDate(LocalDateTime.now());
        this.setUpdatedBy(updatedBy);
    }
}
