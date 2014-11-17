package com.springapp.mvc;

import com.springapp.mvc.dto.ActionDto;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/action")
public class ActionController {

    private static final Logger logger = Logger.getLogger(ActionController.class);

    private static final String CREATE_ACTION = "/create";
    private static final String GET_ACTION = "/get";

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private AreaRepository areaRepository;

    /*
     * Create new action and save its searching areas.
     *
     * Areas (geometries param) given as structure with fields:
     *  - (int) numberInAction
     *  - (string) name
     *  - (string) geometry
     */
    @RequestMapping(value = CREATE_ACTION, method = RequestMethod.POST)
    public @ResponseBody
    Long createAction(
            @RequestParam("name") String name,
            @RequestParam("startDateTime") long startDateTime,
            @RequestParam("description") String description) {

        logger.info(CREATE_ACTION +
                "\nname: " + name +
                "\nstartDateTime: " + startDateTime +
                "\nopis: " + description);
        //TODO: Zapisać akcję do bazy, dla akcji zapisać wszystkie obszary.
        //TODO: Zwrocić id dodanej akcji, lub -1 na fail
        Action action = new Action();
        action.setName(name);
        action.setStartDate(new Timestamp(new Date().getTime()));

        return actionRepository.saveAction(action).getId();
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public @ResponseBody
    ActionDto getAction(
            @RequestParam("id") Long id) {

        logger.info("getting action " + id);
        Action action = actionRepository.getActionById(id);
        Set<Area> areas = areaRepository.getByAction(action);
        action.setAreas(areas);
        return new ActionDto(action);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String start() {
        return "action";
    }
}
