package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Group;
import com.springapp.mvc.repository.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepository {

    @Autowired
    GroupDao groupDao;

    public Group save(Group group) {
        return groupDao.saveAndFlush(group);
    }

}
