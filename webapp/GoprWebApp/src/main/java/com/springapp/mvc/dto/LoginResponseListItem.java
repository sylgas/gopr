package com.springapp.mvc.dto;

public class LoginResponseListItem {
    private long actionId;
    private long userInActionId;
    private String actionName;

    public LoginResponseListItem(){}

    public LoginResponseListItem(long actionId, long userInActionId, String actionName) {
        this.actionId = actionId;
        this.userInActionId = userInActionId;
        this.actionName = actionName;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public void setUserInActionId(long userInActionId) {
        this.userInActionId = userInActionId;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public long getActionId() {
        return actionId;
    }

    public long getUserInActionId() {
        return userInActionId;
    }

    public String getActionName() {
        return actionName;
    }
}
