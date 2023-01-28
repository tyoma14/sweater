package com.artzhelt.sweater.domain;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String text;
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private SweaterUser author;

    public Message() {
    }

    public Message(String text, String tag, SweaterUser author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "%anonymous%";
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SweaterUser getAuthor() {
        return author;
    }

    public void setAuthor(SweaterUser author) {
        this.author = author;
    }
}