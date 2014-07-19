package com.springapp.mvc;

import com.esri.core.geometry.Geometry;
import com.esri.core.map.Graphic;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class LayerRestController {
    private static final Logger logger = Logger.getLogger(LayerRestController.class);

    public static final String GET_LAYER = "/rest/layer/get";
    public static final String SEND_LAYER = "/rest/layer/send";
    public static final String GET_POINTS = "/rest/point/get";
    public static final String SEND_POINTS = "/rest/point/send";
    public static final String GET_ALL_POINTS = "/rest/point/getAllPoints";

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
     * JSONObject.geometries = Array<Area>
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

    @RequestMapping(value = GET_POINTS, method = RequestMethod.GET)
    public
    @ResponseBody
    String passPoints(@RequestParam("actionId") int actionId, @RequestParam("dateTime") long dateTime) {
        logger.info("GET LAYER: " + actionId);

        Date date = new Date(dateTime);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        logger.info(df.format(date));

        JSONObject json1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject coordinate1 = new JSONObject();
        coordinate1.put("x", -8864114.480484284);
        coordinate1.put("y", 4362469.970217699);
        jsonArray1.put(coordinate1);
        json1.put("userId", "8");
        json1.put("positions", jsonArray1);

        JSONObject json2 = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject coordinate2 = new JSONObject();
        coordinate2.put("x", -9584114.480484284);
        coordinate2.put("y", 3962469.970217699);
        JSONObject coordinate3 = new JSONObject();
        coordinate3.put("x", 2214749.0606268025);
        coordinate3.put("y", 6200923.093105682);
        jsonArray2.put(coordinate2);
        jsonArray2.put(coordinate3);
        json2.put("userId", "5");
        json2.put("positions", jsonArray2);

        JSONObject json3 = new JSONObject();
        JSONArray jsonArray3 = new JSONArray();
        json3.put("userId", "1");
        json3.put("positions", jsonArray3);

        JSONArray result = new JSONArray();
        result.put(json1);
        result.put(json2);
        result.put(json3);

        logger.info(result.toString());

        return result.toString();
    }

    @RequestMapping(value = SEND_POINTS, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean receivePoints(@RequestParam("actionId") long actionId, @RequestParam("positions") JSONObject positions) {
        logger.info("SEND LEYER: action: " + actionId + ", pola:\n\t" + positions.toString());

        //TODO: save jsonobject["geometries"] to db )

        return true;
    }

    @RequestMapping(value = GET_ALL_POINTS, method = RequestMethod.GET)
    public
    @ResponseBody
    String passAllPoints(@RequestParam("actionId") int actionId) {
        logger.info("GET LAYER: " + actionId);

        JSONObject json1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject coordinate1 = new JSONObject();
        coordinate1.put("x", -8864114.480484284);
        coordinate1.put("y", 4362469.970217699);
        jsonArray1.put(coordinate1);
        json1.put("userId", "8");
        json1.put("positions", jsonArray1);

        JSONObject json2 = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject coordinate2 = new JSONObject();
        coordinate2.put("x", -9664114.480484284);
        coordinate2.put("y", 3962469.970217699);
        JSONObject coordinate3 = new JSONObject();
        coordinate3.put("x", 2214749.0606268025);
        coordinate3.put("y", 6460923.093105682);
        jsonArray2.put(coordinate2);
        jsonArray2.put(coordinate3);
        json2.put("userId", "5");
        json2.put("positions", jsonArray2);

        JSONObject json3 = new JSONObject();
        JSONArray jsonArray3 = new JSONArray();
        json3.put("userId", "1");
        json3.put("positions", jsonArray3);

        JSONArray result = new JSONArray();
        result.put(json1);
        result.put(json2);
        result.put(json3);

        logger.info(result.toString());

        return result.toString();
    }
}