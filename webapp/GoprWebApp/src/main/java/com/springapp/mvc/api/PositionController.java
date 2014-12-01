package com.springapp.mvc.api;

import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserInActionRepository userInActionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActionRepository actionRepository;

    /*
     * Method returns list of all positions per user in action
     */
    @RequestMapping(value = ALL_ACTION_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody List<UserPositionsDto>  getAllActionPositions(
            @RequestParam("actionId") int actionId){

        logger.info(ALL_ACTION_POSITIONS +
                "\nactionId: " + actionId);

        List<UserInAction> userInActionList = new ArrayList<UserInAction>();
        for (Group group : groupRepository.getGroupsByAction(actionRepository.get(new Long(actionId)))){
            userInActionList.addAll(userInActionRepository.getUserInActionsByGroup(group));
        }

        List<UserPositionsDto> userInActionPositionList = new ArrayList<UserPositionsDto>();
        for (UserInAction userInAction : userInActionList){
            List<PositionDto> positionDtoList = new ArrayList<PositionDto>();
            for (Position position : positionRepository.getPositionByUserInAction(userInAction)){
                PositionDto positionDto = new PositionDto();
                positionDto.setLatitude(position.getLatitude());
                positionDto.setLongitude(position.getLongitude());
                positionDto.setDateTime(position.getDateTime());
                positionDtoList.add(positionDto);
            }
            UserPositionsDto userPositionsDto = new UserPositionsDto();
            userPositionsDto.setUserInActionId(userInAction.getId());
            userPositionsDto.setPositions(positionDtoList);
            userInActionPositionList.add(userPositionsDto);
        }

        return userInActionPositionList;

        //region tmp
        /*
        JSONArray positions = new JSONArray();

        JSONObject position11 = new JSONObject();
        position11.put("latitude", -8864114.480484284);
        position11.put("longitude", 4362469.970217699);
        position11.put("dateTime", 1);
        JSONObject position12 = new JSONObject();
        position12.put("latitude", -9584114.480484284);
        position12.put("longitude", 4362469.970217699);
        position12.put("dateTime", 2);
        JSONArray user1positions = new JSONArray();
        user1positions.put(position11);
        user1positions.put(position12);

        JSONObject position21 = new JSONObject();
        position21.put("latitude", -2214749.0606268025);
        position21.put("longitude", 6200923.093105682);
        position21.put("dateTime", 1);
        JSONArray user2positions = new JSONArray();
        user2positions.put(position21);

        JSONObject position31 = new JSONObject();
        position31.put("latitude", -1214749.0606268025);
        position31.put("longitude", 3200923.093105682);
        position31.put("dateTime", 1);
        JSONObject position32 = new JSONObject();
        position32.put("latitude", -2214749.0606268025);
        position32.put("longitude", 2100923.093105682);
        position32.put("dateTime", 2);
        JSONObject position33 = new JSONObject();
        position33.put("latitude", -2214749.0606268025);
        position33.put("longitude", 4000923.093105682);
        position33.put("dateTime", 3);
        JSONArray user3positions = new JSONArray();
        user3positions.put(position31);
        user3positions.put(position32);
        user3positions.put(position33);

        JSONObject user1 = new JSONObject();
        user1.put("userInActionId", 1);
        user1.put("positions", user1positions);

        JSONObject user2 = new JSONObject();
        user2.put("userInActionId", 12);
        user2.put("positions", user2positions);

        JSONObject user3 = new JSONObject();
        user3.put("userInActionId", 15);
        user3.put("positions", user3positions);

        positions.put(user1);
        positions.put(user2);
        positions.put(user3);
        */
        //endregion

        //positions.toString();
    }

    /*
     * Method returns list of all positions per user in action after giving date time.
     */
    @RequestMapping(value = ACTION_POSITIONS, method = RequestMethod.GET)
    public @ResponseBody List<UserPositionsDto> getAllActionPositions(
            @RequestParam("actionId") int actionId,
            @RequestParam("afterDateTime") long afterDateTime){

        logger.info(ALL_ACTION_POSITIONS +
                "\nactionId: " + actionId);

        List<UserInAction> userInActionList = new ArrayList<UserInAction>();
        for (Group group : groupRepository.getGroupsByAction(actionRepository.get(new Long(actionId)))){
            userInActionList.addAll(userInActionRepository.getUserInActionsByGroup(group));
        }

        List<UserPositionsDto> userInActionPositionList = new ArrayList<UserPositionsDto>();
        for (UserInAction userInAction : userInActionList){
            List<PositionDto> positionDtoList = new ArrayList<PositionDto>();
            for (Position position : positionRepository.getPositionByUserInActionAfterDateTime(userInAction, new Timestamp(afterDateTime))){
                PositionDto positionDto = new PositionDto();
                positionDto.setLatitude(position.getLatitude());
                positionDto.setLongitude(position.getLongitude());
                positionDto.setDateTime(position.getDateTime());
                positionDtoList.add(positionDto);
            }
            UserPositionsDto userPositionsDto = new UserPositionsDto();
            userPositionsDto.setUserInActionId(userInAction.getId());
            userPositionsDto.setPositions(positionDtoList);
            userInActionPositionList.add(userPositionsDto);
        }

        return userInActionPositionList;


        //region tmp
        /*
        JSONArray positions = new JSONArray();

        JSONObject position11 = new JSONObject();
        position11.put("latitude", -9564114.480484284);
        position11.put("longitude", 5562469.970217699);
        position11.put("dateTime", 1);
        JSONArray user1positions = new JSONArray();
        user1positions.put(position11);

        JSONObject position21 = new JSONObject();
        position21.put("latitude", -2214749.0606268025);
        position21.put("longitude", 6200923.093105682);
        position21.put("dateTime", 1);
        JSONObject position22 = new JSONObject();
        position22.put("latitude", -1214749.0606268025);
        position22.put("longitude", 5200923.093105682);
        position22.put("dateTime", 2);
        JSONObject position23 = new JSONObject();
        position23.put("latitude", -20214749.0606268025);
        position23.put("longitude", 7200923.093105682);
        position23.put("dateTime", 3);
        JSONArray user2positions = new JSONArray();
        user2positions.put(position21);
        user2positions.put(position22);
        user2positions.put(position23);

        JSONArray user3positions = new JSONArray();

        JSONObject user1 = new JSONObject();
        user1.put("userInActionId", 1);
        user1.put("positions", user1positions);

        JSONObject user2 = new JSONObject();
        user2.put("userInActionId", 12);
        user2.put("positions", user2positions);

        JSONObject user3 = new JSONObject();
        user3.put("userInActionId", 15);
        user3.put("positions", user3positions);

        positions.put(user1);
        positions.put(user2);
        positions.put(user3);
        */
        //endregion

        //return positions.toString();
    }


}
