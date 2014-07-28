package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Layer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Created by Paulina on 2014-07-18.
 */
public interface LayerDao extends JpaRepository<Layer, Long> {

    Collection<Layer> findOneByName(String name);

    Collection<Layer> findOneByAction(Action action);
}