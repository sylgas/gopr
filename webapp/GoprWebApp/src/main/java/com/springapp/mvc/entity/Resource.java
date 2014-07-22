package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Paulina on 2014-07-21.
 */
@Entity
@Table(name = "t_resource", schema = "public", catalog = "gopr")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "resource_data", nullable = true, insertable = true, updatable = true)
    private byte[] resourceData;

    @Basic
    @Column(name = "note_type", nullable = true, insertable = true, updatable = true, length = 10)
    private String noteType;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "resource_id")
    private Note note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getResourceData() {
        return resourceData;
    }

    public void setResourceData(byte[] resourceData) {
        this.resourceData = resourceData;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public Note getNote() { return note; }

    public void setNote(Note note) { this.note = note; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) return false;
        if (noteType != null ? !noteType.equals(resource.noteType) : resource.noteType != null) return false;
        if (!Arrays.equals(resourceData, resource.resourceData)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (resourceData != null ? Arrays.hashCode(resourceData) : 0);
        result = 31 * result + (noteType != null ? noteType.hashCode() : 0);
        return result;
    }
}
