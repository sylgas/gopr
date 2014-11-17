package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.MessageDao;
import com.agh.gopr.app.database.entity.Message;
import com.google.inject.Inject;

public class MessageDaoProvider extends AbstractDaoProvider<MessageDao> {

    @Inject
    protected MessageDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, Message.class);
    }

    public MessageDao get() {
        return getDao();
    }
}
