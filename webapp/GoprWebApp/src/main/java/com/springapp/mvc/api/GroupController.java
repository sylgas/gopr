package com.springapp.mvc.api;

import com.springapp.mvc.dto.GroupDto;
import com.springapp.mvc.entity.*;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import com.springapp.mvc.repository.GroupRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {
    private static final Logger logger = Logger.getLogger(GroupController.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private AreaRepository areaRepository;

    /*
     * Saves group of given action.
     */
    @RequestMapping(value = "/api/group", method = RequestMethod.POST)
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

    @RequestMapping(value = "/api/group/action/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<GroupDto> getByActionId(
            @PathVariable("id") Long id) {
        List<GroupDto> groups = new ArrayList<GroupDto>();
        for(Group group: groupRepository.getByAction(actionRepository.get(id))) {
            groups.add(new GroupDto(group));
        }
        return groups;
    }

    @RequestMapping(value = "/api/group", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupDto> getAll() {
        List<GroupDto> groups = new ArrayList<GroupDto>();
        for(Group group: groupRepository.getAll()) {
            groups.add(new GroupDto(group));
        }
        return groups;
    }
}
