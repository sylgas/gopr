package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Paulina on 2014-07-16.
 */
@Entity
@Table(name = "t_role", schema = "public", catalog = "gopr")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 25)
    private String name;

    @OneToMany(mappedBy = "role")
    private Collection<User> users;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role that = (Role) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
