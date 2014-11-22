package com.agh.gopr.app.service;

import com.agh.gopr.app.database.dao.NoteDao;
import com.agh.gopr.app.database.entity.Note;
import com.agh.gopr.app.database.entity.Position;

import java.sql.SQLException;

import javax.inject.Inject;

import roboguice.util.Ln;

public class NoteService {

    @Inject
    private NoteDao noteDao;

    @Inject
    private PositionService positionService;

    public boolean save(Note note){
        if ((note.getText() == null || note.getText().isEmpty())
            && (note.getResourcePath() == null || note.getResourcePath().isEmpty()))
            return false;

        Position position = positionService.getMostRecentPosition();
        note.setPosition(position);
        try {
            noteDao.create(note);
        } catch (SQLException e) {
            Ln.e(e, "Could not save note: %s", position);
            return false;
        }
        return true;
    }
}
