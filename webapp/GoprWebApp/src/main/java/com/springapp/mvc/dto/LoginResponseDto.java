package com.springapp.mvc.dto;

import java.util.*;

public class LoginResponseDto {
    private boolean status;
    List<LoginResponseListItem> LoginResponseListItems;

    public LoginResponseDto(boolean status, List<LoginResponseListItem> loginResponseListItems) {
        this.status = status;
        LoginResponseListItems = loginResponseListItems;
    }

    public boolean isStatus() {
        return status;
    }

    public List<LoginResponseListItem> getLoginResponseListItems() {
        return LoginResponseListItems;
    }
}
