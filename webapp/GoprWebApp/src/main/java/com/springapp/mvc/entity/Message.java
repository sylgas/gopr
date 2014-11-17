package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "t_message", schema = "public", catalog = "gopr")
public class Message {

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

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "id")
    private UserInAction fromUser;

    @OneToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @OneToMany(mappedBy = "message")
    private Collection<MessageToUser> messageToUsers;

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

    public UserInAction getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserInAction fromUser) {
        this.fromUser = fromUser;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Collection<MessageToUser> getMessageToUsers() {
        return messageToUsers;
    }

    public void setMessageToUsers(Collection<MessageToUser> messageToUsers) {
        this.messageToUsers = messageToUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

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
