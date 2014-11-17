package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AreaDao extends JpaRepository<Area, Long> {

    Collection<Area> findOneByName(String name);

    Collection<Area> findOneByAction(Action action);

    Collection<Area> findByAction(Action action);
}