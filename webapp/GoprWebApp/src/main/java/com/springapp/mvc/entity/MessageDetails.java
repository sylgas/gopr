package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_message_details", schema = "public", catalog = "gopr")
public class MessageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "date_time", nullable = false, insertable = true, updatable = true)
    private Timestamp dateTime;

    @Basic
    @Column(name = "text", nullable = true, insertable = true, updatable = true, length = 250)
    private String text;

    @OneToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "id")
    private User fromUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDetails that = (MessageDetails) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

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
