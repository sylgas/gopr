package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Group;
import com.springapp.mvc.entity.GroupArea;

import java.util.ArrayList;
import java.util.Collection;

public class GroupDto {
    private Long id;
    private String name;
    private String color;
    private String pictogram;
    private Collection<AreaDto> areas;
    private ActionDto action;

    public GroupDto() {}

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.areas = new ArrayList<AreaDto>();
        for(GroupArea groupArea: group.getGroupAreas()) {
            areas.add(new AreaDto(groupArea.getArea()));
        }
        this.action = new ActionDto(group.getAction());
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPictogram() {
        return pictogram;
    }

    public void setPictogram(String pictogram) {
        this.pictogram = pictogram;
    }

    public Collection<AreaDto> getAreas() {
        return areas;
    }

    public void setAreas(Collection<AreaDto> areas) {
        this.areas = areas;
    }

    public ActionDto getAction() {
        return action;
    }

    public void setAction(ActionDto action) {
        this.action = action;
    }
}
