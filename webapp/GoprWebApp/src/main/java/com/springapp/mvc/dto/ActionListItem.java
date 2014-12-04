package com.springapp.mvc.dto;

public class ActionListItem {

    private long actionId;

    private long userInActionId;

    private long groupId;

    private String actionName;

    public ActionListItem() {
    }

    public ActionListItem(long actionId, long userInActionId, long groupId, String actionName) {
        this.actionId = actionId;
        this.userInActionId = userInActionId;
        this.groupId = groupId;
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserInActionId() {
        return userInActionId;
    }

    public String getActionName() {
        return actionName;
    }
}
