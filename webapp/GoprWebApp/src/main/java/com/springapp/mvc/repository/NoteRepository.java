package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Note;
import com.springapp.mvc.repository.dao.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class NoteRepository {

    @Autowired
    public NoteDao noteDao;

    public Note saveNote(Note note) {
        return noteDao.saveAndFlush(note);
    }

    public Collection<Note> getNotes() { return noteDao.findAll(); }
}
