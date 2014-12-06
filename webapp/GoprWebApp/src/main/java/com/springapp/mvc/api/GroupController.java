package com.springapp.mvc.api;

import com.springapp.mvc.dto.GroupDto;
import com.springapp.mvc.dto.UserInActionDto;
import com.springapp.mvc.entity.*;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import com.springapp.mvc.repository.GroupRepository;
import com.springapp.mvc.repository.UserInActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/group/")
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserInActionRepository userInActionRepository;

    /*
     * Saves group of given action.
     */
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    GroupDto create(@RequestParam("actionId") long actionId,
                    @RequestParam("name") String name,
                    @RequestParam("areaId") long areaId) {
        Group group = new Group(name, actionRepository.get(actionId));
        GroupArea groupArea = new GroupArea();
        groupArea.setGroup(group);
        groupArea.setArea(areaRepository.get(areaId));
        group.getGroupAreas().add(groupArea);
        group = groupRepository.save(group);
        return new GroupDto(group);
    }

    @RequestMapping(value = "action/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupDto> getByActionId(
            @PathVariable("id") long id) {
        List<GroupDto> groups = new ArrayList<GroupDto>();
        for (Group group : groupRepository.getByAction(actionRepository.get(id))) {
            GroupDto groupDto = new GroupDto(group);
            List<UserInActionDto> actionUsers = new ArrayList<UserInActionDto>();
            for (UserInAction user : userInActionRepository.getByGroup(group)) {
                System.out.println("stgggg");
                actionUsers.add(new UserInActionDto(user));
            }
            groupDto.setActionUsers(actionUsers);
            groups.add(groupDto);
        }
        return groups;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupDto> getAll() {
        List<GroupDto> groups = new ArrayList<GroupDto>();
        for (Group group : groupRepository.getAll()) {
            groups.add(new GroupDto(group));
        }
        return groups;
    }
}
