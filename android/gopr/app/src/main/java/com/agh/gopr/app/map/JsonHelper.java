package com.agh.gopr.app.map;

import android.graphics.Color;

import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.response.PositionDto;
import com.agh.gopr.app.response.PositionListResponse;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.google.gson.Gson;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

public class JsonHelper {
    private static final int TRANSPARENT_GREY = Color.argb(30, 58, 58, 58);
    public static final String GEOMETRIES_FIELD = "geometries";
    public static final String AREA_FIELD = "area";
    public static final String USER_ID_FIELD = "userId";
    public static final String POSITIONS_FIELD = "positions";
    public static final String ACTION_ID_FIELD = "actionId";
    public static final String DATE_TIME_FIELD = "dateTime";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    public static List<Graphic> readLayer(String json/*, Preferences_ preferences*/) throws Exception {
        List<Graphic> areas = new ArrayList<Graphic>();

        JSONTokener tokener = new JSONTokener(json);
        JSONObject root = new JSONObject(tokener);

        //String actionId = root.getString(ACTION_ID_FIELD);
        //preferences.actionId().put(actionId);

        JSONArray graphicArray = root.getJSONArray(GEOMETRIES_FIELD);

        JsonFactory factory = new JsonFactory();

        for (int i = 0; i < graphicArray.length(); i++) {
            JsonParser parser = factory.createJsonParser(graphicArray.getJSONObject(i).getString(AREA_FIELD));
            MapGeometry mapGeometry = GeometryEngine.jsonToGeometry(parser);

            Graphic newArea = new Graphic(mapGeometry.getGeometry(),
                    new SimpleFillSymbol(TRANSPARENT_GREY, SimpleFillSymbol.STYLE.SOLID));
            areas.add(newArea);
        }
        return areas;
    }

    public static void readUserPositions(String json, UserPositionProcessListener listener) {
        Gson gson = new Gson();
        PositionListResponse[] positionListResponses = gson.fromJson(json, PositionListResponse[].class);
        for (PositionListResponse positionListResponse : positionListResponses) {
            for (PositionDto positionDto : positionListResponse.getPositions()) {
                Position position = positionDto.toPosition();
                position.setUserId(String.valueOf(positionListResponse.getUserInActionId()));
                listener.process(position);
            }
        }
    }

    public static JSONObject createJsonFromPositions(String name, List<Position> positionList) throws JSONException {
        //TODO: change it to sending PositionDto
        JSONObject json = new JSONObject();
        Ln.d("Post: " + name);
        json.put(USER_ID_FIELD, name);
        JSONArray positions = new JSONArray();
        for (Position position : positionList) {
            positions.put(position.toJSON());
        }
        json.put(POSITIONS_FIELD, positions);
        return json;
    }

    public interface UserPositionProcessListener {
        void process(Position position);
    }
}