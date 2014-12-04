package com.springapp.mvc.dto;

import java.sql.Timestamp;

public class PositionDto {
    private double latitude;
    private double longitude;
    private Timestamp dateTime;

    public PositionDto(){}

    public PositionDto(double latitude, double longitude, Timestamp dateTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
}
