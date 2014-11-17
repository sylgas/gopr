package com.springapp.mvc.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_message_to_user", schema = "public", catalog = "gopr")
@IdClass(MessageToUserId.class)
public class MessageToUser {

    @Id
    @Column(name = "to_user_id")
    private Long toUserId;

    @Id
    @Column(name = "message_id")
    private Long messageDetailsId;

    @ManyToOne
    @JoinColumn(name = "to_user_id", updatable = false, insertable = false, referencedColumnName = "id")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "message_id", updatable = false, insertable = false, referencedColumnName = "id")
    private Message message;

    private User getToUser() {
        return toUser;
    }

    private void setToUser(User toUser) {
        this.toUser = toUser;
    }

    private Message getMessage() {
        return message;
    }

    private void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageToUser that = (MessageToUser) o;

        if (message != null ? !message.equals(that.message) : that.message != null)
            return false;
        if (toUserId != null ? !toUserId.equals(that.toUserId) : that.toUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = toUserId != null ? toUserId.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
