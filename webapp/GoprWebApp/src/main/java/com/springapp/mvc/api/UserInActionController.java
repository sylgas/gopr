package com.springapp.mvc.api;

import com.springapp.mvc.entity.Group;
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
            @RequestParam("userId") long userId,
            @RequestParam("groupId") long groupId,
            @RequestParam(value = "phone", required = false) String phone) {
        UserInAction userInAction = new UserInAction();
        if(phone != null) {
            userInAction.setPhone(phone);
        }
        Group group = groupRepository.get(groupId);
        userInAction.setGroup(group);
        userInAction.setUser(userRepository.get(userId));
        return userInActionRepository.save(userInAction);
    }
}
