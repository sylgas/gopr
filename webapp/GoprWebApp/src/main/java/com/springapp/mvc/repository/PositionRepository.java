package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.dao.PositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Repository
public class PositionRepository {

    @Autowired
    public PositionDao positionDao;

    public Position savePosition(Position position) {
        return positionDao.saveAndFlush(position);
    }

    public Collection<Position> getPositions() { return positionDao.findAll(); }

    public Collection<Position> getPositionByUserInAction(UserInAction userInAction) {
        return positionDao.findAllByUserInAction(userInAction);
    }

    public Collection<Position> getPositionByUserInActionAfterDateTime(UserInAction userInAction, Timestamp dateTime) {
        return positionDao.findByAllByUserInActionAndDateTimeGreaterThan(userInAction, dateTime);
    }
}
