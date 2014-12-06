package com.springapp.mvc.dto;

import java.util.*;

public class LoginResponse {

    private boolean status;

    private List<ActionListItem> actionListItems;

    public LoginResponse(boolean status, List<ActionListItem> actionListItems) {
        this.status = status;
        this.actionListItems = actionListItems;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ActionListItem> getActionListItems() {
        return actionListItems;
    }

    public void setActionListItems(List<ActionListItem> actionListItems) {
        this.actionListItems = actionListItems;
    }
}
