package com.springapp.mvc.api;

import com.springapp.mvc.dto.UserDto;
import com.springapp.mvc.dto.UserInActionDto;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.GroupRepository;
import com.springapp.mvc.repository.UserInActionRepository;
import com.springapp.mvc.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    UserInAction create(
            @RequestParam("userId") long userId,
            @RequestParam("groupId") long groupId,
            @RequestParam(value = "phone", required = false) String phone) {
        UserInAction userInAction = new UserInAction();
        if (phone != null) {
            userInAction.setPhone(phone);
        }
        Group group = groupRepository.get(groupId);
        userInAction.setGroup(group);
        userInAction.setUser(userRepository.get(userId));
        return userInActionRepository.save(userInAction);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/group/{id}")
    public
    @ResponseBody
    List<UserInActionDto> getByGroupId(@PathVariable("id") long groupId) {
        List<UserInActionDto> userInActions = new ArrayList<UserInActionDto>();
        for (UserInAction userInAction : userInActionRepository.getByGroup(groupRepository.get(groupId))) {
            UserInActionDto userInActionDto = new UserInActionDto(userInAction);
            userInActionDto.setUser(new UserDto(userInAction.getUser()));
            userInActions.add(userInActionDto);
        }
        return userInActions;
    }
}
