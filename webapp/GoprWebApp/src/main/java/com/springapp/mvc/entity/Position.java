package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "t_position", schema = "public", catalog = "gopr")
public class Position {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "position_seq", sequenceName = "t_position_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "position_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "latitude", nullable = false, precision = 5)
    private Double latitude;

    @Basic
    @Column(name = "longitude", nullable = false, precision = 5)
    private Double longitude;

    @Basic
    @Column(name = "date_time", nullable = false)
    private Timestamp dateTime;

    @ManyToOne
    @JoinColumn(name = "user_in_action_id")
    private UserInAction userInAction;

    @OneToMany(mappedBy = "position")
    private Collection<Note> notes;

    public Position() {}

    public Position(UserInAction userInAction, Double latitude, Double longitude, Timestamp dateTime) {
        this.userInAction = userInAction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserInAction getUserInAction() {
        return userInAction;
    }

    public void setUserInAction(UserInAction userInAction) {
        this.userInAction = userInAction;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNotes(Collection<Note> notes) {
        this.notes = notes;
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
