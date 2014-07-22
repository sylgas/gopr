package com.springapp.mvc.entity;

import javax.persistence.*;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_user_group", schema = "public", catalog = "gopr")
@IdClass(UserGroupId.class)
public class UserGroup {

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    private Long userId;

    @Id
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Group group;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    private User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
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

        UserGroup that = (UserGroup) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
