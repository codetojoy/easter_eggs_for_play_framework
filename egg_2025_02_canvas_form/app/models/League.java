package models;

import jakarta.persistence.*;
import io.ebean.annotation.DbEnumValue;

@Entity 
public class League {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    // will break: @Enumerated(EnumType.ORDINAL) -- because of Roster using STRING
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

    // will break: @DbEnumValue  -- because of Roster using STRING
    public RosterStatus getStatus() {
        return status;
    }

    public void setStatus(RosterStatus status) {
        this.status = status;
    }
}

