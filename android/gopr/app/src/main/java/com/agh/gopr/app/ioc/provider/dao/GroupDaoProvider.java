package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.GroupDao;
import com.agh.gopr.app.database.entity.Group;
import com.google.inject.Inject;

public class GroupDaoProvider extends AbstractDaoProvider<GroupDao> {

    @Inject
    protected GroupDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, Group.class);
    }

    public GroupDao get() {
        return getDao();
    }
}
