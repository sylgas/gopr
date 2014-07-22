package com.springapp.mvc.entity;

import javax.persistence.*;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_message_to_user", schema = "public", catalog = "gopr")
@IdClass(MessageToUserId.class)
public class MessageToUser {

    @Id
    @Column(name = "to_user_id")
    private Long toUserId;

    @Id
    @Column(name = "message_details_id")
    private Long messageDetailsId;

    @ManyToOne
    @JoinColumn(name = "to_user_id", updatable = false, insertable = false, referencedColumnName = "id")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "message_details_id", updatable = false, insertable = false, referencedColumnName = "id")
    private MessageDetails messageDetails;

    private User getToUser() {
        return toUser;
    }

    private void setToUser(User toUser) {
        this.toUser = toUser;
    }

    private MessageDetails getMessageDetails() {
        return messageDetails;
    }

    private void setMessageDetails(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageToUser that = (MessageToUser) o;

        if (messageDetails != null ? !messageDetails.equals(that.messageDetails) : that.messageDetails != null)
            return false;
        if (toUserId != null ? !toUserId.equals(that.toUserId) : that.toUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = toUserId != null ? toUserId.hashCode() : 0;
        result = 31 * result + (messageDetails != null ? messageDetails.hashCode() : 0);
        return result;
    }
}
