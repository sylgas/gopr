package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionDao extends JpaRepository<Position, Long> {
}
