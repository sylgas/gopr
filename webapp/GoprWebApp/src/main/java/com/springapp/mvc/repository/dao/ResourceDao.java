package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceDao extends JpaRepository<Resource, Long> {
}
