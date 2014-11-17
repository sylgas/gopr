package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.UserDao;
import com.agh.gopr.app.database.entity.User;
import com.google.inject.Inject;

public class UserDaoProvider extends AbstractDaoProvider<UserDao> {

    @Inject
    protected UserDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, User.class);
    }

    public UserDao get() {
        return getDao();
    }
}
