package com.agh.gopr.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.agh.gopr.app.database.entity.Group;
import com.agh.gopr.app.database.entity.Message;
import com.agh.gopr.app.database.entity.Note;
import com.agh.gopr.app.database.entity.Position;
import com.agh.gopr.app.database.entity.User;
import com.agh.gopr.app.database.entity.UserGroup;
import com.google.inject.Singleton;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import javax.inject.Inject;

import roboguice.util.Ln;

@Singleton
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "gopr.db";

    private static final int DATABASE_VERSION = 1;

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserGroup.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Group.class);
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, Position.class);
            TableUtils.createTable(connectionSource, Note.class);
        } catch (SQLException e) {
            Ln.e(e, "Could not create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
