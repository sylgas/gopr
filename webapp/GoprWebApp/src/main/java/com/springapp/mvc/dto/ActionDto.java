package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.Group;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class ActionDto {
    private Long id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private String comment;
    private String staticDatabaseId;
    private String isridDatabaseId;
    private List<Group> groups;
    private List<AreaDto> areas;

    public ActionDto() {
    }

    public ActionDto(Action action) {
        this.id = action.getId();
        this.name = action.getName();
        this.description = action.getDescription();
        this.startDate = action.getStartDate();
        areas = new ArrayList<AreaDto>();
        for (Area area : action.getAreas()) {
            areas.add(new AreaDto(area));
        }
        if(action.getGroups().size() > 0) {
            groups = new ArrayList<Group>();
            for (Group group : action.getGroups()) {
                groups.add(group);
            }
        }
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStaticDatabaseId() {
        return staticDatabaseId;
    }

    public void setStaticDatabaseId(String staticDatabaseId) {
        this.staticDatabaseId = staticDatabaseId;
    }

    public String getIsridDatabaseId() {
        return isridDatabaseId;
    }

    public void setIsridDatabaseId(String isridDatabaseId) {
        this.isridDatabaseId = isridDatabaseId;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<AreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDto> areas) {
        this.areas = areas;
    }

}
