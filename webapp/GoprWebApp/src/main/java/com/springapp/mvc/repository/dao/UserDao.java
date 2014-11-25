package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Long> {

    User findBySurname(String surname);

    List<User> findAll();

    @Query("select DISTINCT(u) from User u where u.name like :query or u.surname like :query")
    List<User> findByQuery(@Param("query") String query);

}