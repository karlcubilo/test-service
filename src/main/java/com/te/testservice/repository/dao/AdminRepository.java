package com.te.testservice.repository.dao;

import com.te.testservice.repository.dto.AdminDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminDto,Long> {

    AdminDto findByLastNameAndFirstName(String lastName, String firstName);
}
