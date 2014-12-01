package com.springapp.mvc.dto;

import java.sql.Timestamp;

public class PositionDto {
    private Double latitude;
    private Double longitude;
    private Timestamp dateTime;

    public PositionDto(){}

    public PositionDto(Double latitude, Double longitude, Timestamp dateTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
}
