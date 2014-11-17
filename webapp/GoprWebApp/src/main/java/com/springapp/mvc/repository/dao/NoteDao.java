package com.springapp.mvc.repository.dao;

import com.springapp.mvc.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDao extends JpaRepository<Note, Long> {
}
