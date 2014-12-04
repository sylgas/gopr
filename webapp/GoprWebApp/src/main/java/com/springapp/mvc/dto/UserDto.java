package com.springapp.mvc.dto;

import com.springapp.mvc.entity.User;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class UserDto {

    private long id;

    private String name;

    private String surname;

    private String login;

    private String phone;

    private String nick;

    /*private Role role;*/

    public UserDto() {}

    public UserDto(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.nick = user.getNick();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }*/

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
