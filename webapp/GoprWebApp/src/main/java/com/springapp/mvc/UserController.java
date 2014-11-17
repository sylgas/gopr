package com.springapp.mvc;

import com.springapp.mvc.dto.LoginResponseDto;
import com.springapp.mvc.dto.LoginResponseListItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    private static final String LOGIN = "/login";

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
}
