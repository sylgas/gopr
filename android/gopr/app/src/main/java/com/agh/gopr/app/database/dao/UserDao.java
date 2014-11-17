package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class UserDao extends BaseDaoImpl<User, Long> {
    public UserDao(Class<User> dataClass) throws SQLException {
        super(dataClass);
    }

    public UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public UserDao(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
