package com.springapp.mvc.repository;

import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public User save(User user) {
        return userDao.saveAndFlush(user);
    }

    public User get(Long userId) {
        return userDao.findOne(userId);
    }

    public List<User> getAll() { return new ArrayList<User>(userDao.findAll()); }

    public User getBySurname(String surname) {
        return userDao.findBySurname(surname);
    }

    public User getByLoginAndPassword(String login, String password) {
        return userDao.findByLoginAndPassword(login, password);
    }
}
