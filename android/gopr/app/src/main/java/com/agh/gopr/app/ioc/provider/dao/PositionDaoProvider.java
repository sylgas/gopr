package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.PositionDao;
import com.agh.gopr.app.database.entity.Position;
import com.google.inject.Inject;

public class PositionDaoProvider extends AbstractDaoProvider<PositionDao> {

    @Inject
    protected PositionDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, Position.class);
    }

    public PositionDao get() {
        return getDao();
    }
}
