package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.repository.dao.AreaDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AreaRepository {

    private static final Logger logger = Logger.getLogger(AreaRepository.class);

    @Autowired
    AreaDao areaDao;

    public Area save(Area area) {
        return areaDao.saveAndFlush(area);
    }

    public Area get(Long areaId) {
        return areaDao.findOne(areaId);
    }

    public Set<Area> getByName(String name) { return new HashSet<Area>(areaDao.findOneByName(name)); }

    public Set<Area> getByAction(Action action) {
        return new HashSet<Area>(areaDao.findByAction(action)); }

    public int getAmount() {
        return areaDao.findAll().size();
    }

    public Collection<Area> getAll() { return areaDao.findAll(); }
}
