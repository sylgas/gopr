package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Action;
import com.springapp.mvc.entity.Area;
import com.springapp.mvc.entity.Group;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActionDto {
    private Long id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private String comments;
    private String staticDatabaseId;
    private String isridDatabaseId;
    private List<Group> groups;
    private List<AreaDto> areas;

    public ActionDto(Action action) {
        this.id = action.getId();
        this.name = action.getName();
        this.description = action.getDescription();
        this.startDate = action.getStartDate();
        areas = new ArrayList<AreaDto>();
        for(Area area: action.getAreas()) {
            areas.add(new AreaDto(area));
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    /* public Collection<Group> getGroups() {
         return groups;
     }

     public void setGroups(Collection<Group> groups) {
         this.groups = groups;
     }
 */
    public List<AreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDto> areas) {
        this.areas = areas;
    }

}
