package com.springapp.mvc.entity;

import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "t_action")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "t_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
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

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "comments", nullable = true)
    private String comments;

    @Basic
    @Column(name = "static_database_id", nullable = true)
    private Long staticDatabaseId;

    @Basic
    @Column(name = "isrid_database_id", nullable = true)
    private Long isridDatabaseId;


    @OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Group> groups;

    @OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Area> areas;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getStaticDatabaseId() {
        return staticDatabaseId;
    }

    public void setStaticDatabaseId(Long staticDatabaseId) {
        this.staticDatabaseId = staticDatabaseId;
    }

    public Long getIsridDatabaseId() {
        return isridDatabaseId;
    }

    public void setIsridDatabaseId(Long isridDatabaseId) {
        this.isridDatabaseId = isridDatabaseId;
    }

   public Set<Group> getGroups() { return groups; }

    public void setGroups(Set<Group> groups) { this.groups = groups; }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }
}
