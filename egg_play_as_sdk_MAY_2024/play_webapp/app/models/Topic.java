package models;

import jakarta.persistence.*;

import java.util.*;

@Entity 
public class Topic {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

/*
    @ManyToMany()
    @JoinTable(name="book_topic",
        joinColumns= @JoinColumn(name="book_id", referencedColumnName="id"),
        inverseJoinColumns= @JoinColumn(name="topic_id", referencedColumnName="id")
    )
    private List<Book> books;
*/

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

/*
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
*/
}
