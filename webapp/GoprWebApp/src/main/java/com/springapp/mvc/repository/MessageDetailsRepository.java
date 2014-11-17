package com.springapp.mvc.repository;

import com.springapp.mvc.repository.dao.MessageDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDetailsRepository {

    @Autowired
    MessageDetailsDao messageDetailsDao;
}
