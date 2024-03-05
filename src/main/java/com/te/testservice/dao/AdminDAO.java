package com.te.testservice.dao;

import com.te.testservice.dao.dto.AdminDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO extends JpaRepository<AdminDto,Long> {
}
