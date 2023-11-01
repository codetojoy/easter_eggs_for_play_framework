package models;

import play.data.validation.Constraints;

import jakarta.persistence.Entity;

/**
 * Account entity managed by Ebean
 */
@Entity 
public class Account {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private String accountId;

    @Constraints.Required
    private String username;

    @Constraints.Required
    private String status;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

