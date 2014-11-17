package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
