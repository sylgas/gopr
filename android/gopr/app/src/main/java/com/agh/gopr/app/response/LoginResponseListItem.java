package com.agh.gopr.app.response;

import java.io.Serializable;

public class LoginResponseListItem implements Serializable{
    private String actionId;
    private String userInActionId;
    private String actionName;

    public LoginResponseListItem(String actionId, String userInActionId, String actionName) {
        this.actionId = actionId;
        this.userInActionId = userInActionId;
        this.actionName = actionName;
    }

    public String getActionId() {
        return actionId;
    }

    public String getUserInActionId() {
        return userInActionId;
    }

    public String getActionName() {
        return actionName;
    }
}
