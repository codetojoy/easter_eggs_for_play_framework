package models;

import jakarta.persistence.*;

import java.util.*;

@Entity 
public class Book {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String author;

/*
    @ManyToMany()
    @JoinTable(name="book_topic",
        joinColumns= @JoinColumn(name="topic_id", referencedColumnName="id"),
        inverseJoinColumns= @JoinColumn(name="book_id", referencedColumnName="id")
    )
    private List<Topic> topics;
*/

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

/*
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
*/
}

