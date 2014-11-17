package com.springapp.mvc.api;

import com.springapp.mvc.dto.LoginResponseDto;
import com.springapp.mvc.dto.LoginResponseListItem;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    private static final String LOGIN = "/login";

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = LOGIN, method = RequestMethod.GET)
    public
    @ResponseBody
    LoginResponseDto loginUser(
            @RequestParam("login") int login,
            @RequestParam("password") int password) {

        logger.info("LOGIN: " + login + " " + password);

        //TODO: sprawdzanie poprawnosci log i haslo + pobranie opdpowienich danych do wyslania

        List<LoginResponseListItem> items = new ArrayList<LoginResponseListItem>();
        items.add(new LoginResponseListItem(1, 1, "KROWA"));
        LoginResponseDto result = new LoginResponseDto(true, items);
        return result;
    }

    /*
     * Returns all system users.
     */
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
