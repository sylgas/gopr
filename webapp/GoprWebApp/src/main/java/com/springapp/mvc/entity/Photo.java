package com.springapp.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_photo", schema = "public", catalog = "gopr")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "photo_data", nullable = false, insertable = true, updatable = true)
    private byte[] photoData;

    @Basic
    @Column(name = "date_time", nullable = false, insertable = true, updatable = true)
    private Timestamp dateTime;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "photo_id")
    private MessageDetails messageDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public MessageDetails getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (dateTime != null ? !dateTime.equals(photo.dateTime) : photo.dateTime != null) return false;
        if (id != null ? !id.equals(photo.id) : photo.id != null) return false;
        if (!Arrays.equals(photoData, photo.photoData)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (photoData != null ? Arrays.hashCode(photoData) : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

}
