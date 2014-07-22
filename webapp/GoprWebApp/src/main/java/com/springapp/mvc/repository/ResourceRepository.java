package com.springapp.mvc.repository;

import com.springapp.mvc.repository.dao.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Paulina on 2014-07-21.
 */

@Repository
public class ResourceRepository {

    @Autowired
    public ResourceDao resourceDao;
}
