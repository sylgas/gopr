package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "t_role", schema = "public", catalog = "gopr")
public class Role {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "role_seq", sequenceName = "t_role_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "role_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 25)
    private String name;

    @Basic
    @Column(name = "color", nullable = true)
    private String color;

    @Basic
    @Column(name = "pictogram", nullable = true)
    private String pictogram;

   /* @OneToMany(mappedBy = "role")
    private Collection<User> users;*/

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPictogram() {
        return pictogram;
    }

    public void setPictogram(String pictogram) {
        this.pictogram = pictogram;
    }

   /* public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }*/

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
