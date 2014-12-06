package com.springapp.mvc.api;

import com.springapp.mvc.dto.LoginResponse;
import com.springapp.mvc.dto.ActionListItem;
import com.springapp.mvc.dto.UserDto;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.UserInActionRepository;
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

    @Autowired
    private UserInActionRepository userInActionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    User create(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("nick") String nick,
            @RequestParam("phone") String phone) {

        User user = new User(name, surname, login, password, nick, phone);
        return userRepository.save(user);
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.GET)
    public
    @ResponseBody
    LoginResponse login(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {

        logger.info("LOGIN: " + login + " / " + password);

        User user = userRepository.getByLoginAndPassword(login, password);

        if (user != null) {
            List<ActionListItem> items = new ArrayList<ActionListItem>();
            List<UserInAction> userInActions = userInActionRepository.getByUser(user);

            for (UserInAction uia : userInActions) {
                ActionListItem item = new ActionListItem();
                item.setUserInActionId(uia.getId());
                item.setActionId(uia.getGroup().getAction().getId());
                item.setGroupId(uia.getGroup().getId());
                item.setActionName(uia.getGroup().getAction().getName());
                items.add(item);
            }

            return new LoginResponse(true, items);
        }

        LoginResponse result = new LoginResponse(false, new ArrayList<ActionListItem>());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserDto> getAll() {
        List<UserDto> users = new ArrayList<UserDto>();
        for (User user : userRepository.getAll()) {
            UserDto userDto = new UserDto(user);
            userDto.setId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setPhone(user.getPhone());
            users.add(userDto);
        }
        return users;
    }
}
