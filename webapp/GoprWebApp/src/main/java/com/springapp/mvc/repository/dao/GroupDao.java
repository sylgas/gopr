package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Group;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group, Long> {

    Collection<Group> findAllByAction(Action action);
}
