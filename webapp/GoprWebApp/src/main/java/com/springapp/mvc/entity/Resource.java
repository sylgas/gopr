package com.springapp.mvc.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "t_resource", schema = "public", catalog = "gopr")
public class Resource {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "resource_seq", sequenceName = "t_resource_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "resource_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "resource")
    private Collection<Note> notes;

    @OneToOne(mappedBy = "resource")
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNotes(Collection<Note> notes) {
        this.notes = notes;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
