package com.agh.gopr.app.response;

import java.util.List;

public class LoginResponse {
    private boolean status;
    List<BasicActionInfo> loginResponseListItems;

    public LoginResponse(boolean status, List<BasicActionInfo> basicActionInfos) {
        this.status = status;
        this.loginResponseListItems = basicActionInfos;
    }

    public boolean getStatus() {
        return status;
    }

    public List<BasicActionInfo> getBasicActionInfos() {
        return loginResponseListItems;
    }
}
