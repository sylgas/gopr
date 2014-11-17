package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.Group;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class GroupDao extends BaseDaoImpl<Group, Long> {
    public GroupDao(Class<Group> dataClass) throws SQLException {
        super(dataClass);
    }

    public GroupDao(ConnectionSource connectionSource, Class<Group> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GroupDao(ConnectionSource connectionSource, DatabaseTableConfig<Group> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
