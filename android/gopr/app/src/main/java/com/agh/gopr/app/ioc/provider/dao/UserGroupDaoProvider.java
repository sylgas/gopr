package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.UserGroupDao;
import com.agh.gopr.app.database.entity.UserGroup;
import com.google.inject.Inject;

public class UserGroupDaoProvider extends AbstractDaoProvider<UserGroupDao> {

    @Inject
    protected UserGroupDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, UserGroup.class);
    }

    public UserGroupDao get() {
        return getDao();
    }
}
