package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDetailsDao extends JpaRepository<Area, Long> {
}
