package com.springapp.mvc.entity;

import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_group", schema = "public", catalog = "gopr")
public class Group {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "group_seq", sequenceName = "t_group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "group_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "pictogram")
    private String pictogram;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<UserInAction> userInActions;

    @OneToMany(mappedBy = "pk.group", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<GroupArea> groupAreas;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Action action;

    public Group(String name, Action action) {
        this.name = name;
        this.action = action;
        this.groupAreas = new HashSet<GroupArea>();
    }

    public Group() {
        this.groupAreas = new HashSet<GroupArea>();
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

    public Set<GroupArea> getGroupAreas() {
        return groupAreas;
    }

    public void setGroupAreas(Set<GroupArea> groupAreas) {
        this.groupAreas = groupAreas;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Set<UserInAction> getUserInActions() {
        return userInActions;
    }

    public void setUserInActions(Set<UserInAction> userInActions) {
        this.userInActions = userInActions;
    }
}
