package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "t_group", schema = "public", catalog = "gopr")
public class Group {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "group_seq", sequenceName = "t_group_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "group_seq", strategy = GenerationType.SEQUENCE)
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

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Collection<GroupArea> groupAreas;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = true)
    private Action action;

    public Group(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public Group() {

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

    public Collection<GroupArea> getGroupAreas() {
        return groupAreas;
    }

    public void setGroupAreas(Collection<GroupArea> groupAreas) {
        this.groupAreas = groupAreas;
    }

    public Action getActionerGroups() {
        return action;
    }

    public void setActionerGroups(Action action) {
        this.action = action;
    }
}
