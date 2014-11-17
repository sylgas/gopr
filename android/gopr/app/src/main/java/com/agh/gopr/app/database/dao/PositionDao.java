package com.agh.gopr.app.database.dao;

import com.agh.gopr.app.database.entity.Position;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PositionDao extends BaseDaoImpl<Position, Long> {
    public PositionDao(Class<Position> dataClass) throws SQLException {
        super(dataClass);
    }

    public PositionDao(ConnectionSource connectionSource, Class<Position> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PositionDao(ConnectionSource connectionSource, DatabaseTableConfig<Position> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<Position> findByUserIdFromDate(String userId, Date fromDate) throws SQLException {
        return queryBuilder().orderBy(Position.Columns.DATE, true).where()
                .eq(Position.Columns.USER_ID, userId)
                .and()
                .gt(Position.Columns.DATE, fromDate)
                .query();
    }

    public List<Position> findAllFromDateExceptUserId(String userId, Date fromDate) throws SQLException {
        return queryBuilder().orderBy(Position.Columns.DATE, true).where()
                .gt(Position.Columns.DATE, fromDate)
                .and()
                .not()
                .eq(Position.Columns.USER_ID, userId)
                .query();
    }

    public long countByUserIdFromDate(String userId, Date fromDate) throws SQLException {
        return queryBuilder().where()
                .eq(Position.Columns.USER_ID, userId)
                .and()
                .gt(Position.Columns.DATE, fromDate)
                .countOf();
    }

    public Position findMostRecentForUserId(String userId) throws SQLException {
        return queryBuilder().orderBy(Position.Columns.DATE, false).where()
                .eq(Position.Columns.USER_ID, userId)
                .queryForFirst();
    }

    public long countByUserIdAndDate(String userId, Date date) throws SQLException {
        return queryBuilder()
                .where()
                .eq(Position.Columns.USER_ID, userId)
                .and()
                .eq(Position.Columns.DATE, date)
                .countOf();
    }
}
