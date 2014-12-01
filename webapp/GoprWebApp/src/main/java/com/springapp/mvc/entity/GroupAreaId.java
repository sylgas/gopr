package com.springapp.mvc.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupAreaId that = (GroupAreaId) o;

        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (area != null ? area.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
