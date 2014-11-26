package com.springapp.mvc.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_group_area")
@AssociationOverrides({
        @AssociationOverride(name = "pk.area",
                joinColumns = @JoinColumn(name = "area_id")),
        @AssociationOverride(name = "pk.group",
                joinColumns = @JoinColumn(name = "group_id")) })
public class GroupArea {

    @EmbeddedId
    private GroupAreaId pk = new GroupAreaId();

    public GroupAreaId getPk() {
        return pk;
    }

    public void setPk(GroupAreaId pk) {
        this.pk = pk;
    }

    @Transient
    public Area getArea() {
        return pk.getArea();
    }

    public void setArea(Area area) {
        getPk().setArea(area);
    }

    @Transient
    public Group getGroup() {
        return pk.getGroup();
    }

    public void setGroup(Group group) {
        getPk().setGroup(group);
    }
}
