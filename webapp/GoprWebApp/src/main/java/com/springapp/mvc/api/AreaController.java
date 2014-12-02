package com.springapp.mvc.api;

import com.springapp.mvc.dto.LayerDto;
import com.springapp.mvc.dto.TmpLayerDto;
import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import com.springapp.mvc.repository.dao.AreaDao;
import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.swing.BakedArrayList;

import java.sql.SQLException;
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
        if (action != null){
            List<Area> areas = new ArrayList<Area>(areaRepository.getByAction(action));
            List<LayerDto> geometries = new ArrayList<LayerDto>();
            for (Area a : areas)
                geometries.add(new LayerDto(a.getNumber(), a.getData(), a.getName()));
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
    boolean saveArea(@RequestParam("actionId") long actionId, @RequestParam("geometries") List<LayerDto> geometries) {
        logger.info("SEND LAYER: action: " + actionId + ", pola:\n\t" + geometries.toString());

        //TODO: save jsonobject["geometries"] to db )

        Action action = actionRepository.get(actionId);
        try {
            Area area = new Area();

            area.setAction(action);
            area.setData(geometries.toString());
            area.setName("first");
            area.setDateTime(new Timestamp(new Date().getTime()));
            area.setIsActive(true);
            areaRepository.save(area);
        } catch (SQLGrammarException e) {
            for (Throwable ex = e; ex != null; ex = e.getCause())
                if (ex instanceof SQLException) { ex = ((SQLException)ex).getNextException();ex.printStackTrace(); }
        }
        return true;
    }
}