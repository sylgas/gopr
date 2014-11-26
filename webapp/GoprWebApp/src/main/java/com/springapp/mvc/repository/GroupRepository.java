package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.repository.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GroupRepository {

    @Autowired
    GroupDao groupDao;

    public Group save(Group group) {
        return groupDao.saveAndFlush(group);
    }

    public Group get(Long groupId) {
        return groupDao.findOne(groupId);
    }

    public Set<Group> getByAction(Action action) {
        System.out.println(groupDao.findByAction(action).size());
        return new HashSet<Group>(groupDao.findByAction(action));
    }

    public List<Group> getAll() {
        return new ArrayList<Group>(groupDao.findAll());
    }
}
