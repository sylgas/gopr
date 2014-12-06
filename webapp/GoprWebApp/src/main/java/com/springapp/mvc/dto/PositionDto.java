package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Note;
import com.springapp.mvc.entity.Position;
import com.springapp.mvc.entity.UserInAction;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.sql.Timestamp;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class PositionDto {
    private long id;
    private double latitude;
    private double longitude;
    private Timestamp dateTime;
    private UserInAction userInAction;
    private List<Note> notes;

    public PositionDto(){}

    public PositionDto(Position position) {
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
        this.dateTime = position.getDateTime();
        this.userInAction = position.getUserInAction();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserInAction getUserInAction() {
        return userInAction;
    }

    public void setUserInAction(UserInAction userInAction) {
        this.userInAction = userInAction;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
