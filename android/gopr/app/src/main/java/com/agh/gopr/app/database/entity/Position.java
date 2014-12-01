package com.agh.gopr.app.database.entity;

import android.location.Location;

import com.agh.gopr.app.database.dao.PositionDao;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

@DatabaseTable(tableName = "position", daoClass = PositionDao.class)
public class Position {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = Columns.ACTION_ID, canBeNull = false)
    private String actionId;

    @DatabaseField(columnName = Columns.USER_ID, canBeNull = false)
    private String userId;

    @DatabaseField(columnName = Columns.LONGITUDE, canBeNull = false)
    private double longitude;

    @DatabaseField(columnName = Columns.LATITUDE, canBeNull = false)
    private double latitude;

    @DatabaseField(columnName = Columns.DATE, canBeNull = false, dataType = DataType.DATE_LONG)
    private Date date;

    public Position() {
    }

    public Position(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static Position fromLocation(Location location) {
        checkNotNull(location);
        return new Position(location.getLongitude(), location.getLatitude());
    }

    public static Position fromJson(JSONObject object) throws JSONException {
        double latitude = (Double) object.get(Columns.LATITUDE);
        double longitude = (Double) object.get(Columns.LONGITUDE);
        return new Position(longitude, latitude);
    }

    /*
     * Convert GPS geographical location to ArcGis format.
     */
    public Point toPoint(SpatialReference mapSpatialReference) {
        Point location = new Point(longitude, latitude);
        Point mapPoint = (Point) GeometryEngine.project(
                location,
                SpatialReference.create(SpatialReference.WKID_WGS84),
                mapSpatialReference);
        return mapPoint;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(Columns.LONGITUDE, longitude);
        object.put(Columns.LATITUDE, latitude);
        object.put(Columns.DATE, date.getTime());
        return object;
    }

    public static class Columns {
        public static final String ACTION_ID = "action_id";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String DATE = "date";
        public static final String USER_ID = "user_id";
    }

}
