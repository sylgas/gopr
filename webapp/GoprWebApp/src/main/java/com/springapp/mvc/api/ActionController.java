package com.springapp.mvc.api;

import com.springapp.mvc.dto.ActionDto;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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


    /*
    * Create new action and save its searching areas.
    *
    * Areas (geometries param) given as structure with fields:
    *  - (int) numberInAction
    *  - (string) name
    *  - (string) geometry
    */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Long create(
            @RequestParam("name") String name,
            @RequestParam("startDateTime") long startDateTime,
            @RequestParam("description") String description) {

        logger.info("create " +
                "\nname: " + name +
                "\nstartDateTime: " + startDateTime +
                "\nopis: " + description);
        //TODO: Zapisać akcję do bazy, dla akcji zapisać wszystkie obszary.
        //TODO: Zwrocić id dodanej akcji, lub -1 na fail
        Action action = new Action();
        action.setName(name);
        action.setStartDate(new Timestamp(new Date().getTime()));

        return actionRepository.save(action).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ActionDto get(
            @PathVariable("id") Long id) {

        logger.info("getting action " + id);
        Action action = actionRepository.get(id);
        Set<Area> areas = areaRepository.getByAction(action);
        action.setAreas(areas);
        return new ActionDto(action);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<Action> getAll() {
        return actionRepository.getAll();
    }
}
