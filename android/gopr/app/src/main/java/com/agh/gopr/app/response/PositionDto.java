package com.agh.gopr.app.response;

import com.agh.gopr.app.database.entity.Position;

import java.io.Serializable;
import java.util.Date;

public class PositionDto implements Serializable {
    private double latitude;
    private double longitude;
    private long dateTime;

    public PositionDto() {
    }

    public PositionDto(double latitude, double longitude, long dateTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public Position toPosition() {
        Position position = new Position(longitude, latitude);
        position.setDate(new Date(dateTime));
        return position;
    }

    public static PositionDto fromPosition(Position position) {
        return new PositionDto(position.getLongitude(), position.getLatitude(), position.getDate().getTime());
    }
}
