package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group, Long> {
}
