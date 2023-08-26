package models;

import io.ebean.*;
import io.ebean.bean.*;
import io.ebean.annotation.*;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.*;

/**
 * entity managed by Ebean
 */
@Entity 
public class Plan {

    private static final long serialVersionUID = 1L;

    @Id
    @Constraints.Required
    private Long id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String status;

    @Constraints.Required
    @DbJsonB
    private List<String> payload;

    public List<String> getPayload() {
        return payload;
    }

    public void setPayload(List<String> payload) {
        this.payload = payload;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

