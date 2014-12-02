package com.springapp.mvc.api;

import java.sql.Timestamp;
import java.util.*;

import com.google.common.collect.HashBiMap;
import com.springapp.mvc.dto.PositionDto;
import com.springapp.mvc.dto.UserPositionsDto;
import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.UserInAction;
import com.springapp.mvc.repository.ActionRepository;
import com.springapp.mvc.repository.GroupRepository;
import com.springapp.mvc.repository.PositionRepository;
import com.springapp.mvc.repository.UserInActionRepository;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/positions")
public class PositionController {
    private static final Logger logger = Logger.getLogger(PositionController.class);

    private static final String ALL_ACTION_POSITIONS = "/action_all";
    private static final String ACTION_POSITIONS = "/action";
    private static final String ALL_USER_POSITIONS = "/user_all";
    private static final String USER_POSITIONS = "/user";
    private static final String SEND_POINTS = "/post";

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserInActionRepository userInActionRepository;

    /*
     * Method returns list of all positions per user in action
     */
    @RequestMapping(value = ALL_ACTION_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody List<UserPositionsDto>  getAllActionPositions(
            @RequestParam("actionId") int actionId){

        logger.info(ALL_ACTION_POSITIONS +
                "\nactionId: " + actionId);

        List<Position> positions = new ArrayList<Position>(positionRepository.getPositions());
        Map<Long, List<PositionDto>> userToPositions = new HashMap<Long, List<PositionDto>>();

        for (Position p : positions){
            try{
                if (!userToPositions.containsKey(p.getUserInAction().getId()))
                    userToPositions.put(p.getUserInAction().getId(), new ArrayList<PositionDto>());

                userToPositions.get(p.getUserInAction().getId()).add(new PositionDto(p.getLatitude(), p.getLongitude(), p.getDateTime()));
            } catch (Exception e){
                //continue...
            }
        }

        List<UserPositionsDto> result = new ArrayList<UserPositionsDto>();
        for (Long id : userToPositions.keySet()){
            result.add(new UserPositionsDto(id, userToPositions.get(id)));
        }

        return result;
    }

    /*
     * Method returns list of all positions per user in action after giving date time.
     */
    @RequestMapping(value = ACTION_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody List<UserPositionsDto> getAllActionPositions(
            @RequestParam("actionId") int actionId,
            @RequestParam("dateTime") long dateTime){

        logger.info(ACTION_POSITIONS +
                "\nactionId: " + actionId +
                "\ndateTime: " + dateTime);

        //List<Position> positions = new ArrayList<Position>(positionRepository.getPositionsAfterDateTime(new Timestamp(dateTime)));
        List<Position> positions = new ArrayList<Position>(positionRepository.getPositions());
        Map<Long, List<PositionDto>> userToPositions = new HashMap<Long, List<PositionDto>>();

        for (Position p : positions){
            try{
                if (!userToPositions.containsKey(p.getUserInAction().getId()))
                    userToPositions.put(p.getUserInAction().getId(), new ArrayList<PositionDto>());

                userToPositions.get(p.getUserInAction().getId()).add(new PositionDto(p.getLatitude(), p.getLongitude(), p.getDateTime()));
            } catch (Exception e){
                //continue...
            }
        }

        List<UserPositionsDto> result = new ArrayList<UserPositionsDto>();
        for (Long id : userToPositions.keySet()){
            result.add(new UserPositionsDto(id, userToPositions.get(id)));
        }

        return result;
    }

    @RequestMapping(value = ALL_USER_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody
    List<PositionDto> getAllUserPositions(
            @RequestParam("userInActionId") Long userInActionId){

        logger.info(ALL_USER_POSITIONS +
                "\nuserInActionId: " + userInActionId);

        List<Position> positions = positionRepository.getAllUserPositions(userInActionId);

        List<PositionDto> userPositions = new ArrayList<PositionDto>();
        for (Position p : positions){
            PositionDto positionDto = new PositionDto();
            positionDto.setLatitude(p.getLatitude());
            positionDto.setLongitude(p.getLongitude());
            positionDto.setDateTime(p.getDateTime());
            userPositions.add(positionDto);
        }

        return userPositions;
    }

    @RequestMapping(value = USER_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody
    List<PositionDto> getUserPositionsAfterDateTime(
            @RequestParam("userInActionId") Long userInActionId,
            @RequestParam("dateTime") Timestamp dateTime){

        logger.info(USER_POSITIONS +
                "\nuserInActionId: " + userInActionId +
                "\ndateTime: " + dateTime.getTime());

        List<Position> positions =
                positionRepository.getUserPositionsAfterDateTime(userInActionId, dateTime);

        List<PositionDto> userPositions = new ArrayList<PositionDto>();
        for (Position p : positions){
            PositionDto positionDto = new PositionDto();
            positionDto.setLatitude(p.getLatitude());
            positionDto.setLongitude(p.getLongitude());
            positionDto.setDateTime(p.getDateTime());
            userPositions.add(positionDto);
        }

        return userPositions;
    }

    @RequestMapping(value = SEND_POINTS, method = RequestMethod.POST)
    public
    @ResponseBody
    boolean receivePoints(@RequestParam("actionId") long actionId, @RequestParam("positions") JSONObject positions) {

        logger.info(SEND_POINTS +
                "\nactionId: " + actionId +
                "\npola: " + positions.toString());

        long userInActionId = positions.getLong("userId");
        JSONArray pos = positions.getJSONArray("positions");
        for (int i = 0; i < pos.length(); i++){
            JSONObject p = pos.getJSONObject(i);
            double longitude = p.getDouble("longitude");
            double latitude = p.getDouble("latitude");
            Timestamp dateTime = new Timestamp(p.getLong("date"));

            Position position = new Position();
            position.setUserInAction(userInActionRepository.get(userInActionId));
            position.setLatitude(latitude);
            position.setLongitude(longitude);
            position.setDateTime(dateTime);

            positionRepository.savePosition(position);
        }
        return true;
    }
}
