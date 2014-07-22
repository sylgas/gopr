package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paulina on 2014-07-21.
 */
public interface NoteDao extends JpaRepository<Note, Long> {
}
