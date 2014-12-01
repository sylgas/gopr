package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.UserInAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserInActionDao extends JpaRepository<UserInAction, Long> {

    Collection<UserInAction> findAllByGroup(Group group);
}
