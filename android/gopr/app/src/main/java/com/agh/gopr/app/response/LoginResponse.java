package com.agh.gopr.app.response;

import java.util.*;

public class LoginResponse {
    private boolean status;
    List<LoginResponseListItem> loginResponseListItems;

    public LoginResponse(boolean status, List<LoginResponseListItem> loginResponseListItems) {
        this.status = status;
        this.loginResponseListItems = loginResponseListItems;
    }

    public boolean getStatus() {
        return status;
    }

    public List<LoginResponseListItem> getLoginResponseListItems() {
        return loginResponseListItems;
    }
}
