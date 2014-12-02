package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.dao.UserInActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.*;

@Repository
public class UserInActionRepository {

    @Autowired
    UserInActionDao userInActionDao;

    public UserInAction get(Long userInActionId){
        return userInActionDao.findOne(userInActionId);
    }

    public UserInAction save(UserInAction userInAction) {
        return userInActionDao.saveAndFlush(userInAction);
    }
    public Collection<UserInAction> getUserInActionsByGroup(Group group) {
        return userInActionDao.findAllByGroup(group); }

    public List<UserInAction> getByUser(User user) {
        return new ArrayList<UserInAction>(userInActionDao.findByUser(user));
    }
}
