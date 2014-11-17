package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.repository.dao.ActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ActionRepository {

    @Autowired
    private ActionDao actionDao;

    public Action saveAction(Action action) {
        return actionDao.saveAndFlush(action);
    }

    public Collection<Action> getActions() { return actionDao.findAll(); }

    public Action getActionById(Long id) { return actionDao.findOne(id); }

}
