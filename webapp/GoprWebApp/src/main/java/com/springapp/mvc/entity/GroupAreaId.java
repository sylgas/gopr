package com.springapp.mvc.entity;

import java.io.Serializable;

//import com.google.common.base.Objects;

public class GroupAreaId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long areaId;

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
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
        result = prime * result	+ ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
        // return obj == this || obj instanceof UserGroupId && Objects.equal(userId, ((UserGroupId)obj).userId) && Objects.equal(groupId, ((UserGroupId)obj).teamId);
    }

}
