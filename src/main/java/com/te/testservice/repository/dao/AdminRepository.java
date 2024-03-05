package com.te.testservice.repository.dao;

import com.te.testservice.repository.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {

    AdminEntity findByLastNameAndFirstName(String lastName, String firstName);
}
