package com.springapp.mvc.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_group_area", schema = "public", catalog = "gopr")
@IdClass(GroupAreaId.class)
public class GroupArea {

    @Id
    @Column(name = "area_id", nullable = false, insertable = true, updatable = true)
    private Long areaId;

    @Id
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "area_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "group_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Group group;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    private Area getArea() {
        return area;
    }

    private void setArea(Area area) {
        this.area = area;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupArea that = (GroupArea) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = areaId != null ? areaId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
