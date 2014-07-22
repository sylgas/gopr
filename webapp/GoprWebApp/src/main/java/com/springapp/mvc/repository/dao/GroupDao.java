package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paulina on 2014-07-22.
 */
public interface GroupDao extends JpaRepository<Group, Long> {
}
