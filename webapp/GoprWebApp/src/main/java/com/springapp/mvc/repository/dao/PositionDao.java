package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.UserInAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Collection;

public interface PositionDao extends JpaRepository<Position, Long> {

    Collection<Position> findByDateTimeGreaterThan(Timestamp dateTime);

    Collection<Position> findByUserInActionId(Long userInActionId);

    Collection<Position> findByUserInActionIdAndDateTimeGreaterThan(Long userInActionId, Timestamp dateTime);
}
