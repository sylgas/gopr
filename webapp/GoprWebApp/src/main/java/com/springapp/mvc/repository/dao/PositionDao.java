package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paulina on 2014-07-21.
 */
public interface PositionDao extends JpaRepository<Position, Long> {
}