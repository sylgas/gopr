package com.springapp.mvc.dto;

import java.util.*;

public class LoginResponseDto {
    private boolean status;
    private List<LoginResponseListItem> loginResponseListItems;

    public LoginResponseDto(boolean status, List<LoginResponseListItem> loginResponseListItems) {
        this.status = status;
        this.loginResponseListItems = loginResponseListItems;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<LoginResponseListItem> getLoginResponseListItems() {
        return loginResponseListItems;
    }

    public void setLoginResponseListItems(List<LoginResponseListItem> loginResponseListItems) {
        this.loginResponseListItems = loginResponseListItems;
    }
}
