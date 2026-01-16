package models;

import play.data.validation.Constraints;

import jakarta.persistence.*;

/**
 * entity managed by Ebean
 */
@Entity 
@Table(name="bridge_reference_plan")
public class ReferencePlan {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private String id;

    @Constraints.Required
    private Reference reference;

    @Constraints.Required
    private Plan plan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}

