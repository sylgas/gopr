package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_note", schema = "public", catalog = "gopr")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "date_time", nullable = true, insertable = true, updatable = true)
    private Timestamp dateTime;

    @Basic
    @Column(name = "text", nullable = true, insertable = true, updatable = true, length = 100)
    private String text;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action actionId) {
        this.action = actionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (dateTime != null ? !dateTime.equals(note.dateTime) : note.dateTime != null) return false;
        if (id != null ? !id.equals(note.id) : note.id != null) return false;
        if (text != null ? !text.equals(note.text) : note.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
