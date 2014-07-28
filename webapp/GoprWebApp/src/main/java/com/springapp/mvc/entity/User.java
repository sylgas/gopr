package com.springapp.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Paulina on 2014-07-16.
 */
@Entity
@Table(name = "t_user", schema = "public", catalog = "gopr")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "firstname", nullable = false, length = 25)
    private String firstname;

    @Basic
    @Column(name = "lastname", nullable = false, length = 25)
    private String lastname;

    @Basic
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Collection<Position> positions;

    @OneToMany(mappedBy = "user")
    private Collection<UserGroup> userGroups;

    @OneToMany(mappedBy = "user")
    private Collection<Note> notes;

    public User() {}

    public User(String firstname, String lastname, String phone) {
        this();
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<Position> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        //if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
       //result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
