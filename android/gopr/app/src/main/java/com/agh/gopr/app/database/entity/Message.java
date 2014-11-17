package com.agh.gopr.app.database.entity;

import com.agh.gopr.app.database.dao.MessageDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "message", daoClass = MessageDao.class)
public class Message {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Columns.SENDER_ID)
    private Group sender;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Columns.RECEIVER_ID)
    private Group receiver;

    @DatabaseField(columnName = Columns.DATE, canBeNull = false, dataType = DataType.DATE_LONG)
    private Date date;

    @DatabaseField(columnName = Columns.ACTION_ID, canBeNull = false)
    private String actionId;

    @DatabaseField(columnName = Columns.TEXT, canBeNull = false)
    private String text;

    public Message() {
    }

    public Message(Group sender, Group receiver, Date date, String actionId, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.actionId = actionId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Group getSender() {
        return sender;
    }

    public Group getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return date;
    }

    public String getActionId() {
        return actionId;
    }

    public String getText() {
        return text;
    }

    public static class Columns {
        public static final String SENDER_ID = "sender_id";
        public static final String RECEIVER_ID = "receiver_id";
        public static final String DATE = "date";
        public static final String ACTION_ID = "action_id";
        public static final String TEXT = "text";
    }
}
