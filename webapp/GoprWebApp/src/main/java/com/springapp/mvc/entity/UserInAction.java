package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "t_user_in_action", schema = "public", catalog = "gopr")
public class UserInAction {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_in_action_seq", sequenceName = "t_user_in_action_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_in_action_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @OneToMany(mappedBy = "userInAction")
    private Collection<Position> positions;

    @OneToMany(mappedBy = "fromUser")
    private Collection<Message> message;

    @OneToMany(mappedBy = "toUser")
    private Collection<MessageToUser> messageToUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public Collection<Position> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Position> positions) {
        this.positions = positions;
    }

    public Collection<Message> getMessage() {
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInAction that = (UserInAction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
