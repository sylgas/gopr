package com.springapp.mvc.entity;

import javax.persistence.ManyToOne;
import java.io.Serializable;

//import com.google.common.base.Objects;

public class GroupAreaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Area area;

    @ManyToOne
    private Group group;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /*private Long areaId;

    private Long groupId;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void getGroupId(Long groupId) {
        this.groupId = groupId;
    }*/
}
