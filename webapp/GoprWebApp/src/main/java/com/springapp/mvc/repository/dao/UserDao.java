package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Long> {

    User findByLastname(String lastname);

    List<User> findAll();

    @Query("select DISTINCT(u) from User u where u.firstname like :query or u.lastname like :query")
    List<User> findByQuery(@Param("query") String query);

}