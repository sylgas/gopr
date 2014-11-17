package com.agh.gopr.app.database.entity;

import com.agh.gopr.app.database.dao.UserDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "user", daoClass = UserDao.class)
public class User {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = Columns.PHONE_NUMBER, canBeNull = false)
    private String phoneNumber;

    @DatabaseField(columnName = Columns.USER_ID, canBeNull = false)
    private String userId;

    @ForeignCollectionField(foreignFieldName = UserGroup.Attributes.USER, eager = true)
    private Collection<UserGroup> userGroups = new ArrayList<UserGroup>();

    public User() {
    }

    public Collection<UserGroup> getUserGroups() {
        return userGroups;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public static class Columns {
        public static final String PHONE_NUMBER = "phone_number";
        public static final String USER_ID = "user_id";
    }
}
