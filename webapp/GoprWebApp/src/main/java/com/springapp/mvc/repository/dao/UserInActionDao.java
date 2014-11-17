package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.UserInAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInActionDao extends JpaRepository<UserInAction, Long> {
}
