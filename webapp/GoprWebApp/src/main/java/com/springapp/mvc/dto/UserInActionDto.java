package com.springapp.mvc.dto;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.entity.UserInAction;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Collection;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class UserInActionDto {

    private Long id;

    private String phone;

    private UserDto user;

    private Role role;

    private GroupDto group;

    private Collection<PositionDto> positions;

    /*private Collection<Message> message;

    private Collection<MessageToUser> messageToUsers;*/

    public UserInActionDto() {}

    public UserInActionDto(UserInAction user) {
        this.id = user.getId();
        this.phone = user.getPhone();
        this.user = new UserDto(user.getUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GroupDto getGroup() {
        return this.group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }


    public Collection<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(Collection<PositionDto> positions) {
        this.positions = positions;
    }

    /*public Collection<Message> getMessage() {
        return message;
    }

    public void setMessage(Collection<Message> message) {
        this.message = message;
    }

    public Collection<MessageToUser> getMessageToUsers() {
        return messageToUsers;
    }

    public void setMessageToUsers(Collection<MessageToUser> messageToUsers) {
        this.messageToUsers = messageToUsers;
    }*/
}
