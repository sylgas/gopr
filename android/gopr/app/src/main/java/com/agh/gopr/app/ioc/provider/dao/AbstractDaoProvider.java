package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.google.inject.Provider;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

import roboguice.util.Ln;

public abstract class AbstractDaoProvider<T extends Dao<?, Long>> implements Provider<T> {
    private final Class<?> entityClass;

    private final DatabaseHelper databaseHelper;

    protected AbstractDaoProvider(DatabaseHelper databaseHelper, Class<?> entityClass) {
        this.databaseHelper = databaseHelper;
        this.entityClass = entityClass;
    }

    @SuppressWarnings("unchecked")
    protected T getDao() {
        Dao<?, ?> dao = DaoManager.lookupDao(databaseHelper.getConnectionSource(), entityClass);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(databaseHelper.getConnectionSource(), entityClass);
            } catch (SQLException e) {
                Ln.e(e, "Could not create DAO");
            }
        }
        return (T) dao;
    }
}
