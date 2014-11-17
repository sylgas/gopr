package com.springapp.mvc.api;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.GroupRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupController {
    private static final Logger logger = Logger.getLogger(GroupController.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActionRepository actionRepository;

    /*
     * Saves group of given action.
     */
    @RequestMapping(value = "/api/group", method = RequestMethod.POST)
    public
    @ResponseBody
    Group saveGroup(@RequestParam("actionId") int actionId, @RequestParam("name") String name) {
        logger.info("Save group: " + name);

        Group group = new Group(name, actionRepository.getActionById(new Long(actionId)));
        return groupRepository.save(group);
    }
}
