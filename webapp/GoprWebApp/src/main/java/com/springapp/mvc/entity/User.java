package com.springapp.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "t_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    private Long id;


    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true)
    private String name;

    @Basic
    @Column(name = "surname", nullable = true, insertable = true, updatable = true)
    private String surname;

    @Basic
    @Column(name = "login", nullable = true, insertable = true, updatable = true)
    private String login;

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true)
    private String password;

    @Basic
    @Column(name = "phone", nullable = true, insertable = true, updatable = true)
    private String phone;

    @Basic
    @Column(name = "nick", nullable = true, insertable = true, updatable = true)
    private String nick;

    /*@ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = true)
    private Role role;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
