package com.springapp.mvc.repository;

import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.dao.UserInActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserInActionRepository {

    @Autowired
    UserInActionDao userInActionDao;

    public UserInAction saveUserAccount(UserInAction layer) {
        return userInActionDao.saveAndFlush(layer);
    }
    public Collection<UserInAction> getUserActscoun() { return userInActionDao.findAll(); }

}
