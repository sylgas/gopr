package com.agh.gopr.app.database.entity;

import com.agh.gopr.app.database.dao.UserGroupDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_group", daoClass = UserGroupDao.class)
public class UserGroup {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Columns.USER_ID)
    private User user;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Columns.GROUP_ID)
    private Group group;

    public UserGroup() {
    }

    public UserGroup(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public static class Columns {
        public static final String USER_ID = "user_id";
        public static final String GROUP_ID = "group_id";
    }

    public static class Attributes {
        public static final String GROUP = "group";
        public static final String USER = "user";
    }
}
