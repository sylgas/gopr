package com.springapp.mvc.api;

import com.springapp.mvc.dto.LayerDto;
import com.springapp.mvc.dto.TmpLayerDto;
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
import java.util.List;
import java.util.*;

@Controller
public class AreaController {
    private static final Logger logger = Logger.getLogger(AreaController.class);

    public static final String GET_LAYER = "/api/area/get";
    public static final String SEND_LAYER = "/api/area/send";


    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ActionRepository actionRepository;

    /*
     * Returns searching areas connected with given action.
     */
    @RequestMapping(value = GET_LAYER, method = RequestMethod.GET)
    public
    @ResponseBody
    TmpLayerDto passGeometries(@RequestParam("actionId") long actionId) {
        logger.info("GET LAYER: " + actionId);

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

    /*
     * Save searching areas of given action.
     *
     * Areas giving in structures:
     * JSONObject.geometries = Array<Area>
     *
     * Area = areaId (this is index of grphic in webapp map),
     *        area (this is geometry.toJson() stringify)
     */
    @RequestMapping(value = SEND_LAYER, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean saveArea(
            @RequestParam("actionId") long actionId,
            @RequestParam("numberInAction") int numberInAction,
            @RequestParam("name") String name,
            @RequestParam("geometry") String geometry) {

        logger.info("SEND LAYER");

        Action action = actionRepository.get(actionId);

        Area area = new Area();
        area.setAction(action);
        area.setData(geometry);
        area.setName(name);
        area.setNumber(numberInAction);
        area.setDateTime(new Timestamp(new Date().getTime()));
        area.setIsActive(true);
        areaRepository.save(area);

        return true;
    }
}