package com.springapp.mvc.api;

import com.springapp.mvc.dto.ActionDto;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value="api/action")
public class ActionController {

    private static final Logger logger = Logger.getLogger(ActionController.class);

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private AreaRepository areaRepository;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Long create(
            @RequestParam("name") String name,
            @RequestParam("description") String description) {

        logger.info("create " +
                "\nname: " + name +
                "\nopis: " + description);
        Action action = new Action();
        action.setName(name);
        action.setDescription(description);
        return actionRepository.save(action).getId();
    }

    @RequestMapping(value = "start/{id}", method = RequestMethod.POST)
    public @ResponseBody
    Long start(
            @PathVariable("id") long id) {

        logger.info("getting action " + id);
        Action action = actionRepository.get(id);
        action.setStartDate(new Timestamp(new Date().getTime()));
        return actionRepository.save(action).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ActionDto get(
            @PathVariable("id") long id) {

        logger.info("getting action " + id);
        Action action = actionRepository.get(id);
        Set<Area> areas = areaRepository.getByAction(action);
        action.setAreas(areas);
        ActionDto actionDto = new ActionDto(action);
        actionDto.setStartDate(action.getStartDate());
        return actionDto;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<ActionDto> getAll() {
        List<ActionDto> actions = new ArrayList<ActionDto>();
        for(Action action: actionRepository.getAll()) {
            ActionDto actionDto = new ActionDto(action);
            actionDto.setStartDate(action.getStartDate());
            actions.add(actionDto);
        }
        return actions;
    }
}
