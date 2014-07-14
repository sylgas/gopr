package com.springapp.mvc;

import com.esri.core.geometry.Geometry;
import com.esri.core.map.Graphic;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class LayerRestController {
    private static final Logger logger = Logger.getLogger(LayerRestController.class);

    public static final String GET_LAYER = "/rest/layer/get";
    public static final String SEND_LAYER = "/rest/layer/send";

    /*
     * Returns searching areas connected with given action.
     */
    @RequestMapping(value = GET_LAYER, method = RequestMethod.GET)
    public
    @ResponseBody
    String passGeometries(@RequestParam("actionId") int actionId) {
        logger.info("GET LAYER: " + actionId);

        //TODO: read from db and pass geometries

        return "{\"geometries\":[{\"area\":{\"rings\":[[[1626888.6995589186,7158670.943098946],[2492767.355973165,7158670.943098946],[2492767.355973165,6918964.422396697],[1626888.6995589186,6918964.422396697],[1626888.6995589186,7158670.943098946]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":1},{\"area\":{\"rings\":[[[2013354.3145686672,7306039.634557807],[2098613.6437922814,7158366.144523265],[1928094.9853450526,7158366.144523265],[2013354.3145686672,7306039.634557807]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":2}]}";
    }

    /*
     * Save searching areas of given action.
     *
     * Areas giving in structures:
     * JSONObject geometries = Map<"geometries", Array<Area>>
     *
     * Area = areaId (this is index of grphic in webapp map),
     *        area (this is geometry.toJson() stringify)
     */
    @RequestMapping(value = SEND_LAYER, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean receiveGeometries(@RequestParam("actionId") long actionId, @RequestParam("geometries") JSONObject geometries) {
        logger.info("SEND LEYER: action: " + actionId + ", pola:\n\t" + geometries);

        //TODO: save jsonobject["geometries"] to db )

        return true;
    }
}
