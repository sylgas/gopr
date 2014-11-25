package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.repository.dao.ActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ActionRepository {

    @Autowired
    private ActionDao actionDao;

    public Action save(Action action) {
        return actionDao.saveAndFlush(action);
    }

    public List<Action> getAll() { return new ArrayList<Action>(actionDao.findAll()); }

    public Action getById(Long id) { return actionDao.findOne(id); }

}
