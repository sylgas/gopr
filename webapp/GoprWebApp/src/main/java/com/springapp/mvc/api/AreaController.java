package com.springapp.mvc.api;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.AreaRepository;
import com.springapp.mvc.repository.PositionRepository;
import com.springapp.mvc.repository.UserRepository;
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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class AreaController {
    private static final Logger logger = Logger.getLogger(AreaController.class);

    public static final String GET_LAYER = "/api/area/get";
    public static final String SEND_LAYER = "/api/area/send";
    public static final String GET_POINTS = "/api/point/get";
    public static final String SEND_POINTS = "/api/point/post";
    public static final String GET_ALL_POINTS = "/api/point/get/all";

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;
    /*
     * Returns searching areas connected with given action.
     */
    @RequestMapping(value = GET_LAYER, method = RequestMethod.GET)
    public
    @ResponseBody
    String passGeometries(@RequestParam("actionId") long actionId) {
        logger.info("GET LAYER: " + actionId);

        //TODO: read from db and pass geometries
        Area area = (Area) areaRepository.getByAction(actionRepository.get(actionId)).toArray()[0];
/*
        return "{\"geometries\":[{\"area\":{\"rings\":[[[1626888.6995589186,7158670.943098946],[2492767.355973165,7158670.943098946],[2492767.355973165,6918964.422396697],[1626888.6995589186,6918964.422396697],[1626888.6995589186,7158670.943098946]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":1},{\"area\":{\"rings\":[[[2013354.3145686672,7306039.634557807],[2098613.6437922814,7158366.144523265],[1928094.9853450526,7158366.144523265],[2013354.3145686672,7306039.634557807]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":2}]}";
*/
        //logger.info(area.getData());
        //return area.getData();
        return "";
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
    boolean saveArea(@RequestParam("actionId") long actionId, @RequestParam("geometries") String geometries) {
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

    @RequestMapping(value = GET_POINTS, method = RequestMethod.GET)
    public
    @ResponseBody
    String passPoints(@RequestParam("actionId") int actionId, @RequestParam("dateTime") long dateTime) {
       // logger.info("GET LAYER: " + actionId);
/*
        Date date = new Date(dateTime);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        logger.info(df.format(date));

        Timestamp timestamp = new Timestamp(date.getTime());
        User user = new User("Jan", "Kowalski", "1234");
        userRepository.saveUser(user);

        User user1 = new User("Anna", "Nowak", "32424");
        userRepository.saveUser(user1);

        Action action = actionRepository.getActionById(new Long(actionId));

        Position position = new Position(-8864114.480484284,
                4362469.970217699,
                timestamp,
                action,
                user);
        positionRepository.savePosition(position);

        position = new Position(-9584114.480484284,
                3962469.970217699,
                timestamp,
                action,
                user1);
        positionRepository.savePosition(position);

        position = new Position(-2214749.0606268025,
                6200923.093105682,
                timestamp,
                action,
                user1);
        positionRepository.savePosition(position);
*/
        JSONObject json1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject coordinate1 = new JSONObject();
        coordinate1.put("latitude", 50.069444);
        coordinate1.put("longitude", 21.701389);
        jsonArray1.put(coordinate1);
        json1.put("userId", "8");
        json1.put("positions", jsonArray1);

        JSONObject json2 = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject coordinate2 = new JSONObject();
        coordinate2.put("latitude", 52.483333);
        coordinate2.put("longitude", -1.906389);
        JSONObject coordinate3 = new JSONObject();
        coordinate3.put("latitude", 50.061389);
        coordinate3.put("longitude", 19.938333);
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

       // logger.info(result.toString());

       // Area area = (Area) areaRepository.getByAction(actionRepository.getActionById(new Long(actionId))).toArray()[0];
/*
        return "{\"geometries\":[{\"area\":{\"rings\":[[[1626888.6995589186,7158670.943098946],[2492767.355973165,7158670.943098946],[2492767.355973165,6918964.422396697],[1626888.6995589186,6918964.422396697],[1626888.6995589186,7158670.943098946]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":1},{\"area\":{\"rings\":[[[2013354.3145686672,7306039.634557807],[2098613.6437922814,7158366.144523265],[1928094.9853450526,7158366.144523265],[2013354.3145686672,7306039.634557807]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":2}]}";
*/
      //  logger.info(area.getData());
        //return area.getData();

        return result.toString();
    }

    @RequestMapping(value = SEND_POINTS, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean receivePoints(@RequestParam("actionId") long actionId, @RequestParam("positions") JSONObject positions) {
        logger.info("SEND POINTS: action: " + actionId + ", pola:\n\t" + positions.toString());

        //TODO: save jsonobject["geometries"] to db )
        logger.info(areaRepository.getAmount());
        //Area layer = new Area("first", positions.toString());
        //areaRepository.save(layer);
        //logger.info(areaRepository.getAmount());

        return true;
    }

    @RequestMapping(value = GET_ALL_POINTS, method = RequestMethod.GET)
    public
    @ResponseBody
    String passAllPoints(@RequestParam("actionId") int actionId) {
       // logger.info("GET LAYER: " + actionId);

       /* User user = new User("Jan", "Kowalski", "1234");
        userRepository.saveUser(user);

        User user1 = new User("Anna", "Nowak", "32424");
        userRepository.saveUser(user1);

        Action action = actionRepository.getById(new Long(actionId));

        Position position = new Position(-8864114.480484284,
                4362469.970217699,
                new Timestamp(new Date().getTime()),
                action,
                user);
        positionRepository.savePosition(position);

        position = new Position(-9584114.480484284,
                3962469.970217699,
                new Timestamp(new Date().getTime()),
                action,
                user1);
        positionRepository.savePosition(position);

        position = new Position(-2214749.0606268025,
                6200923.093105682,
                new Timestamp(new Date().getTime()),
                action,
                user1);
        positionRepository.savePosition(position);
*/

        JSONObject json1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject coordinate1 = new JSONObject();
        coordinate1.put("latitude", 50.069444); //x -> longitude, y -> latitude
        coordinate1.put("longitude", 21.701389);
        jsonArray1.put(coordinate1);
        json1.put("userId", "8");
        json1.put("positions", jsonArray1);

        JSONObject json2 = new JSONObject();
        JSONArray jsonArray2 = new JSONArray();
        JSONObject coordinate2 = new JSONObject();
        coordinate2.put("latitude", 52.483333);
        coordinate2.put("longitude", -1.906389);
        JSONObject coordinate3 = new JSONObject();
        coordinate3.put("latitude", 50.061389);
        coordinate3.put("longitude", 19.938333);
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


        //Area area = (Area) areaRepository.getByAction(actionRepository.getActionById(new Long(actionId))).toArray()[0];
/*
        return "{\"geometries\":[{\"area\":{\"rings\":[[[1626888.6995589186,7158670.943098946],[2492767.355973165,7158670.943098946],[2492767.355973165,6918964.422396697],[1626888.6995589186,6918964.422396697],[1626888.6995589186,7158670.943098946]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":1},{\"area\":{\"rings\":[[[2013354.3145686672,7306039.634557807],[2098613.6437922814,7158366.144523265],[1928094.9853450526,7158366.144523265],[2013354.3145686672,7306039.634557807]]],\"spatialReference\":{\"wkid\":102100,\"latestWkid\":3857}},\"areaId\":2}]}";
*/
        //logger.info(area.getData());
        //return area.getData();
     //   System.out.println(result.toString());
        return result.toString();
    }
}