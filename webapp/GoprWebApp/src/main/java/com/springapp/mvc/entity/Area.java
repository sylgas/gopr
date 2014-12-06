package com.springapp.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "area_seq", sequenceName = "t_area_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "area_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "date_time")
    private Timestamp dateTime;

    @Basic
    @Column(columnDefinition="TEXT", name = "data", nullable = false)
    private String data;

    @Basic
    @Column(name = "number", nullable = false)
    private Integer number;

    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Action action;

    @OneToMany(mappedBy = "pk.area", fetch = FetchType.EAGER)
    private Set<GroupArea> groupAreas;

    public Area() {}

    public Area(String name, String data) {
        this();
        this.groupAreas = new HashSet<GroupArea>();
        this.name = name;
        this.data = data;
        this.dateTime = new Timestamp(new Date().getTime());
    }

    public Area(String name, String layerData, Action action) {
        this(name, layerData);
        //this.action = action;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isActive() {
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
