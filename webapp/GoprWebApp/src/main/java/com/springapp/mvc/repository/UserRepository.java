package com.springapp.mvc.repository;

import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Paulina on 2014-07-15.
 */

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public User findUserByLastname(String login) {
        return userDao.findByLastname(login);
    }

    public User saveUser(User layer) {
        return userDao.saveAndFlush(layer);
    }

    public Collection<User> getUsers() { return userDao.findAll(); }

}
