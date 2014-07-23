package com.springapp.mvc.entity;

import javax.persistence.*;

/**
 * Created by Paulina on 2014-07-16.
 */
@Entity
@Table(name = "t_layer")
public class Layer {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", length = 25)
    private String name;

    @Basic
    @Column(columnDefinition="TEXT", name = "layer_data", nullable = false)
    private String layerData;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = true)
    private Action action;

    public Layer() {
    }

    public Layer(String name, String layerData) {
        this();
        this.name = name;
        this.layerData = layerData;
    }

    public Layer(Long id, String name, String layerData) {
        this(name, layerData);
        this.id = id;
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

    public String getLayerData() {
        return layerData;
    }

    public void setLayerData(String layerData) {
        this.layerData = layerData;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layer that = (Layer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (!layerData.equals(that.layerData)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (layerData != null ? layerData.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
