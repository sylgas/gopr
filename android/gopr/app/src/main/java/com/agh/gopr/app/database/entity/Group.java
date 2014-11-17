package com.agh.gopr.app.database.entity;

import com.agh.gopr.app.database.dao.GroupDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "group", daoClass = GroupDao.class)
public class Group {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = Columns.ACTION_ID, canBeNull = false)
    private String actionId;

    @DatabaseField(columnName = Columns.NAME, canBeNull = false)
    private String name;

    @ForeignCollectionField(foreignFieldName = UserGroup.Attributes.GROUP, eager = true)
    private Collection<UserGroup> userGroups = new ArrayList<UserGroup>();

    public Group() {
    }

    public Group(String actionId, String name) {
        this.actionId = actionId;
        this.name = name;
    }

    public Collection<UserGroup> getUserGroups() {
        return userGroups;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public static class Columns {
        public static final String ACTION_ID = "action_id";
        public static final String NAME = "name";
    }
}
