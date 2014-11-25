package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.GroupArea;

import java.security.Timestamp;
import java.util.Collection;

public class AreaDto {
    private Long id;
    private String name;
    private Timestamp dateTime;
    private String data;
    private String number;
    private Boolean isActive;
    private ActionDto action;
    private Collection<GroupArea> groupAreas;

    public AreaDto() {}

    public AreaDto(Area area) {
        this.name = area.getName();
        this.data = area.getData();
        this.id = area.getId();
        //this.action = new ActionDto(area.getAction());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ActionDto getAction() {
        return action;
    }

    public void setAction(ActionDto action) {
        this.action = action;
    }

    public Collection<GroupArea> getGroupAreas() {
        return groupAreas;
    }

    public void setGroupAreas(Collection<GroupArea> groupAreas) {
        this.groupAreas = groupAreas;
    }

}
