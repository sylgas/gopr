package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.dao.PositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class PositionRepository {

    @Autowired
    public PositionDao positionDao;

    public Position savePosition(Position position) {
        return positionDao.saveAndFlush(position);
    }

    public Collection<Position> getPositions() { return positionDao.findAll(); }

    public Collection<Position> getPositionsAfterDateTime(Timestamp dateTime) {
        return positionDao.findByDateTimeGreaterThan(dateTime);
    }

    public List<Position> getAllUserPositions(Long userInActionId){
        return new ArrayList<Position>(positionDao.findByUserInActionId(userInActionId));
    }

    public List<Position> getUserPositionsAfterDateTime(Long userInActionId, Timestamp dateTime){
        return new ArrayList<Position>(
                positionDao.findByUserInActionIdAndDateTimeGreaterThan(userInActionId, dateTime));
    }
}
