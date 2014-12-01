package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GroupDao extends JpaRepository<Group, Long> {

    Collection<Group> findByAction(Action action);
}
