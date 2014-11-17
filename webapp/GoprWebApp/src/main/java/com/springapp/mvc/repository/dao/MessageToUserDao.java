package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.MessageToUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageToUserDao extends JpaRepository<MessageToUser, Long> {
}
