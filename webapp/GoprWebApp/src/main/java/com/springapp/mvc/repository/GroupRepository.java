package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.repository.dao.GroupDao;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Collection<Group> getGroupsByAction(Action action){
        return groupDao.findAllByAction(action);
    }

}
