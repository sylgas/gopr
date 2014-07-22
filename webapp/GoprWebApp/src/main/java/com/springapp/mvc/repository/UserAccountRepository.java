package com.springapp.mvc.repository;

import com.springapp.mvc.entity.UserAccount;
import com.springapp.mvc.repository.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Paulina on 2014-07-18.
 */

@Repository
public class UserAccountRepository {

    @Autowired
    UserAccountDao userAccountDao;

    public UserAccount saveUserAccount(UserAccount layer) {
        return userAccountDao.saveAndFlush(layer);
    }
    public Collection<UserAccount> getUserActscoun() { return userAccountDao.findAll(); }

}
