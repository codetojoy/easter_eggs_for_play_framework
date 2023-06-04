package models;

import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * entity managed by Ebean
 */
@Entity 
@Table(name="bridge_customer_reference")
public class CustomerReference {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private String id;

    @Constraints.Required
    private Reference reference;

    @Constraints.Required
    private Customer customer;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

