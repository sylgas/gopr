package com.springapp.mvc.api;

import com.springapp.mvc.dto.AreaDto;
import com.springapp.mvc.entity.*;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import com.springapp.mvc.repository.GroupRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
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

    //@Autowired
    //private GroupAreaRepository groupAreaRepository;

    /*
     * Saves group of given action.
     */
    @RequestMapping(value = "/api/group", method = RequestMethod.POST)
    public
    @ResponseBody
    Group create(@RequestParam("actionId") long actionId,
                    @RequestParam("name") String name,
                    @RequestParam("areaId") long areaId) {
        logger.info("Save group: " + name + areaId);
        //Area area = areaRepository.getById(new Long(areaId));
        //area.setGroupAreas(area.getGroupAreas().add(new GroupArea()));
        logger.info(actionId + " " + areaId);
        Group group = new Group(name, actionRepository.get(actionId));
        logger.info("group created");
        GroupArea groupArea = new GroupArea();
        groupArea.setGroup(group);
        logger.info("group set");
        groupArea.setArea(areaRepository.get(areaId));
        logger.info("area set");
        group.getGroupAreas().add(groupArea);
        logger.info("added");
        return groupRepository.save(group);
    }
}
