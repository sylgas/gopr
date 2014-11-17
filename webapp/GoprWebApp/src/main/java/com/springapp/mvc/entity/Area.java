package com.springapp.mvc.entity;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "name", length = 25)
    private String name;

    @Basic
    @Column(name = "date_time")
    private Timestamp dateTime;

    @Basic
    @Column(columnDefinition="TEXT", name = "data", nullable = false)
    private String data;

    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = true)
    private Action action;

    @OneToMany(mappedBy = "area")
    @JsonManagedReference
    private Set<GroupArea> groupAreas;

    public Area() {}

    public Area(String name, String data) {
        this();
        this.name = name;
        this.data = data;
    }

    public Area(String name, String layerData, Action action) {
        this(name, layerData);
        this.action = action;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Set<GroupArea> getGroupAreas() {
        return groupAreas;
    }

    public void setGroupAreas(Set<GroupArea> groupAreas) {
        this.groupAreas = groupAreas;
    }
}
