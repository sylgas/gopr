package com.springapp.mvc.api;

import com.springapp.mvc.dto.LoginResponseDto;
import com.springapp.mvc.dto.LoginResponseListItem;
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

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody
    User create(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("nick") String nick,
            @RequestParam("phone") String phone) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        user.setNick(nick);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.GET)
    public
    @ResponseBody
    LoginResponseDto loginUser(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {

        logger.info("LOGIN: " + login + " / " + password);

        User user = userRepository.getByLoginAndPassword(login, password);

        if (user != null){
            List<LoginResponseListItem> items = new ArrayList<LoginResponseListItem>();
            List<UserInAction> userInActions = userInActionRepository.getByUser(user);

            for (UserInAction uia : userInActions){
                LoginResponseListItem item = new LoginResponseListItem();
                item.setUserInActionId(uia.getId());
                item.setActionId(uia.getGroup().getAction().getId());
                item.setActionName(uia.getGroup().getAction().getName());
                items.add(item);
            }

            return new LoginResponseDto(true, items);
        }

        LoginResponseDto result = new LoginResponseDto(false, new ArrayList<LoginResponseListItem>());
        return result;
    }

    /*
     * Returns all system users.
     */
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<UserDto>();
        for(User user: userRepository.getAll()) {
            users.add(new UserDto(user));
        }
        return users;
    }
}
