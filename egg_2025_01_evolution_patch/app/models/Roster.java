package models;

import jakarta.persistence.*;

@Entity 
public class Roster {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RosterStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RosterStatus getStatus() {
        return status;
    }

    public void setStatus(RosterStatus status) {
        this.status = status;
    }
}

