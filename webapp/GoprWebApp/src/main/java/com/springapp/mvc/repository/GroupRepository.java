package com.springapp.mvc.repository;

import com.springapp.mvc.repository.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Paulina on 2014-07-22.
 */

@Repository
public class GroupRepository {

    @Autowired
    GroupDao groupDao;
}
