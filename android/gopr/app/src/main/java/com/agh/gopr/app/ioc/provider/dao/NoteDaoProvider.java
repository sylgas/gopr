package com.agh.gopr.app.ioc.provider.dao;

import com.agh.gopr.app.database.DatabaseHelper;
import com.agh.gopr.app.database.dao.NoteDao;
import com.agh.gopr.app.database.entity.Note;
import com.google.inject.Inject;

public class NoteDaoProvider extends AbstractDaoProvider<NoteDao> {

    @Inject
    protected NoteDaoProvider(DatabaseHelper databaseHelper) {
        super(databaseHelper, Note.class);
    }

    public NoteDao get() {
        return getDao();
    }
}
