package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Paulina on 2014-07-16.
 */
@Entity
@Table(name = "t_action", schema = "public", catalog = "gopr")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50)
    private String name;

    @Basic
    @Column(name = "start_date", nullable = false, insertable = true, updatable = true)
    private Timestamp startDate;

    @Basic
    @Column(name = "end_date", nullable = true, insertable = true, updatable = true)
    private Timestamp endDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "action")
    private Collection<Note> notes;

    @OneToMany(mappedBy = "action")
    private Collection<Position> positions;

    @OneToMany(mappedBy = "action")
    private Collection<Layer> layers;

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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public void settNotesById(Collection<Note> notes) {
        this.notes = notes;
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

        Action that = (Action) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
