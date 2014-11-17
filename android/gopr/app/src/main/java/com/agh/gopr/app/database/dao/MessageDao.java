package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.Message;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class MessageDao extends BaseDaoImpl<Message, Long> {
    public MessageDao(Class<Message> dataClass) throws SQLException {
        super(dataClass);
    }

    public MessageDao(ConnectionSource connectionSource, Class<Message> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public MessageDao(ConnectionSource connectionSource, DatabaseTableConfig<Message> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
