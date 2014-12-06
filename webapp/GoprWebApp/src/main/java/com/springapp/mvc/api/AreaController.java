package com.springapp.mvc.api;

import com.springapp.mvc.dto.LayerDto;
import com.springapp.mvc.dto.TmpLayerDto;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping(value = "/api/area/")
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ActionRepository actionRepository;

    /*
     * Save searching areas of given action.
     *
     * Areas giving in structures:
     * JSONObject.geometries = Array<Area>
     *
     * Area = areaId (this is index of grphic in webapp map),
     *        area (this is geometry.toJson() stringify)
     */
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    long create(
            @RequestParam("actionId") long actionId,
            @RequestParam("numberInAction") int numberInAction,
            @RequestParam("name") String name,
            @RequestParam("geometry") String geometry) {

        Action action = actionRepository.get(actionId);
        Area area = new Area(action, numberInAction, name, geometry, new Timestamp(new Date().getTime()), true);
        return areaRepository.save(area).getId();
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public
    @ResponseBody
    TmpLayerDto getByActionId(@RequestParam("actionId") long actionId) {

        Action action = actionRepository.get(actionId);
        if (action != null) {
            List<Area> areas = new ArrayList<Area>(areaRepository.getByAction(action));
            List<LayerDto> geometries = new ArrayList<LayerDto>();
            for (Area a : areas)
                geometries.add(new LayerDto(a.getData()));
            return new TmpLayerDto(geometries);
        }

        return new TmpLayerDto(new ArrayList<LayerDto>());
    }
}