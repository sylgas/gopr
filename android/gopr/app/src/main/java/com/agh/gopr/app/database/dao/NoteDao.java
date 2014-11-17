package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.Note;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class NoteDao extends BaseDaoImpl<Note, Long> {
    public NoteDao(Class<Note> dataClass) throws SQLException {
        super(dataClass);
    }

    public NoteDao(ConnectionSource connectionSource, Class<Note> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public NoteDao(ConnectionSource connectionSource, DatabaseTableConfig<Note> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
