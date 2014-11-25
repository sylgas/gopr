package com.springapp.mvc.api;

import com.springapp.mvc.dto.LoginResponseDto;
import com.springapp.mvc.dto.LoginResponseListItem;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.GroupRepository;
import com.springapp.mvc.repository.UserInActionRepository;
import com.springapp.mvc.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/api/action/user")
public class UserInActionController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserInActionRepository userInActionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody
    UserInAction create(
            @RequestParam("user") User user,
            @RequestParam("phone") String phone,
            @RequestParam("group") Group group) {
        UserInAction userInAction = new UserInAction();
        userInAction.setPhone(phone);
        userInAction.setGroup(group);
        userInAction.setUser(user);
        return userInActionRepository.save(userInAction);
    }
}