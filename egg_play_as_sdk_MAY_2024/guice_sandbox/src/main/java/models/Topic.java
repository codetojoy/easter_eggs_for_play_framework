package models;

import jakarta.persistence.*;

import java.util.*;

@Entity 
public class Topic {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    // getters, setters

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
}
