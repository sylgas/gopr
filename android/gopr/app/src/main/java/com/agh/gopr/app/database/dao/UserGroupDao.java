package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.UserGroup;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class UserGroupDao extends BaseDaoImpl<UserGroup, Long> {
    public UserGroupDao(Class<UserGroup> dataClass) throws SQLException {
        super(dataClass);
    }

    public UserGroupDao(ConnectionSource connectionSource, Class<UserGroup> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public UserGroupDao(ConnectionSource connectionSource, DatabaseTableConfig<UserGroup> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
