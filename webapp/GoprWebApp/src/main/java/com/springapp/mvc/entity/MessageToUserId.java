package com.springapp.mvc.entity;

/**
 * Created by Paulina on 2014-07-21.
 */

import java.io.Serializable;

//import com.google.common.base.Objects;

public class MessageToUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long toUserId;

    private Long messageDetailsId;

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long userId) {
        this.toUserId = userId;
    }

    public Long getMessageDetailsId() {
        return messageDetailsId;
    }

    public void setMessageDetailsId(Long messageDetailsId) {
        this.messageDetailsId = messageDetailsId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((toUserId == null) ? 0 : toUserId.hashCode());
        result = prime * result	+ ((messageDetailsId == null) ? 0 : messageDetailsId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
        //return obj == this || obj instanceof MessageToUserId && Objects.equal(toUserId, ((MessageToUserId)obj).toUserId) && Objects.equal(messageId, ((MessageToUserId)obj).messageId);
    }

}
