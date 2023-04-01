package models;

import javax.persistence.*;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    public Post() {} 

    public Post(String title, String content, Integer id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
