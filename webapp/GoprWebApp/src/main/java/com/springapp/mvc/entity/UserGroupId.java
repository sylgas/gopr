package com.springapp.mvc.entity;

import java.io.Serializable;

//import com.google.common.base.Objects;

public class UserGroupId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long groupId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result	+ ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
       // return obj == this || obj instanceof UserGroupId && Objects.equal(userId, ((UserGroupId)obj).userId) && Objects.equal(groupId, ((UserGroupId)obj).teamId);
    }

}
