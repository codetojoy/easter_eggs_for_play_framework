package models;

import play.data.validation.Constraints;

import jakarta.persistence.Entity;

/**
 * entity managed by Ebean
 */
@Entity 
public class Customer {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private String id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

