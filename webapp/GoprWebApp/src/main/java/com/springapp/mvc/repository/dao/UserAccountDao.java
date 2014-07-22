package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paulina on 2014-07-18.
 */
public interface UserAccountDao extends JpaRepository<UserAccount, Long> {
}
