package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paulina on 2014-07-21.
 */
public interface ResourceDao extends JpaRepository<Resource, Long> {
}
