package com.agh.gopr.app.database.entity;

import com.agh.gopr.app.database.dao.NoteDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note", daoClass = NoteDao.class)
public class Note {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = Columns.TYPE, canBeNull = false)
    private Type type;

    @DatabaseField(columnName = Columns.TEXT)
    private String text;

    @DatabaseField(columnName = Columns.RESOURCE_PATH)
    private String resourcePath;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Columns.POSITION_ID)
    private Position position;

    public Note() {
    }

    public Note(Type type, String text, String resourcePath, Position position) {
        this.type = type;
        this.text = text;
        this.resourcePath = resourcePath;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Columns {
        public static final String POSITION_ID = "position_id";
        public static final String TYPE = "type";
        public static final String TEXT = "text";
        public static final String RESOURCE_PATH = "resource_path";
    }

    public enum Type {
        TEXT, PHOTO
    }

}
