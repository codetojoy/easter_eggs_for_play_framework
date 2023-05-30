package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;

/**
 * Account entity managed by Ebean
 */
@Entity 
public class Account {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private int accountId;

    @Constraints.Required
    private String username;

    @Constraints.Required
    private String status;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
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

