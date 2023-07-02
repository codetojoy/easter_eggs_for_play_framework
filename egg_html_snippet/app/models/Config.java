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
public class Config {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    private String id;

    @Constraints.Required
    private String snippet;

    @Constraints.Required
    private String template;

    @Constraints.Required
    private String hash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

