package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_position", schema = "public", catalog = "gopr")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "latitude", nullable = false, insertable = true, updatable = true, precision = 5)
    private Double latitude;

    @Basic
    @Column(name = "longitude", nullable = false, insertable = true, updatable = true, precision = 5)
    private Double longitude;

    @Basic
    @Column(name = "date_time", nullable = false, insertable = true, updatable = true)
    private Timestamp dateTime;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Position() {}

    public Position(Double latitude, Double longitude, Timestamp dateTime, Action action, User user) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.action = action;
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (dateTime != null ? !dateTime.equals(position.dateTime) : position.dateTime != null) return false;
        if (id != null ? !id.equals(position.id) : position.id != null) return false;
        if (latitude != null ? !latitude.equals(position.latitude) : position.latitude != null) return false;
        if (longitude != null ? !longitude.equals(position.longitude) : position.longitude != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
