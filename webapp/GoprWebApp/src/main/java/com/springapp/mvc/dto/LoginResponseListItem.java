package com.springapp.mvc.dto;

public class LoginResponseListItem {
    private long actionId;
    private int userInActionId;
    private String actionName;

    public LoginResponseListItem(long actionId, int userInActionId, String actionName) {
        this.actionId = actionId;
        this.userInActionId = userInActionId;
        this.actionName = actionName;
    }

    public long getActionId() {
        return actionId;
    }

    public int getUserInActionId() {
        return userInActionId;
    }

    public String getActionName() {
        return actionName;
    }
}
