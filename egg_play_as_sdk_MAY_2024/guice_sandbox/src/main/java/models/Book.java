package models;

import jakarta.persistence.*;

import java.util.*;

@Entity 
public class Book {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String author;

    @Override
    public String toString() {
        return "id: " + id + " title: " + title + " author: " + author;
    }

    // getters, setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
