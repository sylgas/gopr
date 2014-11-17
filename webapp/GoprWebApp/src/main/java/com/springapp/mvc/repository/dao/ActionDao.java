package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionDao extends JpaRepository<Action, Long> {
}
