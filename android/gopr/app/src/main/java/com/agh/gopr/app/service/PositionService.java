package com.agh.gopr.app.service;

import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.dao.PositionDao;
import com.agh.gopr.app.database.entity.Position;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import roboguice.util.Ln;

public class PositionService {

    @Inject
    private PositionDao positionDao;

    @Inject
    private Preferences_ preferences;

    public Position save(Position position) {
        position.setActionId(preferences.actionId().get());
        try {
            if (!existsForUserIdAndDate(position.getUserId(), position.getDate())) {
                positionDao.createOrUpdate(position);
            }
        } catch (SQLException e) {
            Ln.e(e, "Could not save position: %s", position);
        }
        return position;
    }

    private boolean existsForUserIdAndDate(String userId, Date date) throws SQLException {
        return positionDao.countByUserIdAndDate(userId, date) > 0;
    }

    public long getNotPostedPositionsCount() {
        String userId = preferences.userId().get();
        Date date = new Date(preferences.lastPositionPostTime().get());
        try {
            return positionDao.countByUserIdFromDate(userId, date);
        } catch (SQLException e) {
            Ln.e(e, "Could not get positions for user %s", userId);
            return 0L;
        }
    }

    public List<Position> getNotPostedPositions() {
        return getPositionsFrom(preferences.lastPositionPostTime().get());
    }

    public Position getMostRecentPosition() {
        String userId = preferences.userId().get();
        try {
            return positionDao.findMostRecentForUserId(userId);
        } catch (SQLException e) {
            Ln.e(e, "Could not get positions for user %s", userId);
            return null;
        }
    }

    public List<Position> getPositionsFrom(long date) {
        String userId = preferences.userId().get();
        try {
            return positionDao.findByUserIdFromDate(userId, new Date(date));
        } catch (SQLException e) {
            Ln.e(e, "Could not get positions for user %s", userId);
            return null;
        }
    }

    public List<Position> getReceivedPositionsFrom(long date) {
        String userId = preferences.userId().get();
        try {
            return positionDao.findAllFromDateExceptUserId(userId, new Date(date));
        } catch (SQLException e) {
            Ln.e(e, "Could not get positions for user %s", userId);
            return null;
        }
    }

}
