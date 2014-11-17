package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.repository.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class RoleRepository {

    @Autowired
    RoleDao roleDao;

    public Role saveRole(Role layer) {
        return roleDao.saveAndFlush(layer);
    }

    public Collection<Role> getRoles() { return roleDao.findAll(); }

}
