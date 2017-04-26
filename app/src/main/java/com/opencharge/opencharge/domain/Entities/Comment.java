package com.opencharge.opencharge.domain.Entities;

import java.util.Date;

/**
 * Created by DmnT on 26/04/2017.
 */

public class Comment {

    public String id;
    public String autor;
    public String text;
    public Date data;

    //Empty constructor needed for Firebase
    public Comment() {}


    public Comment(String autor, String text, Date data) {
        this.autor = autor;
        this.text = text;
        this.data = data;
    }

    //Getters and setters


    public String getId(){ return id; }
    public String getAuthor() { return autor; }
    public String getText() {
        return text;
    }
    public Date getDate() {
        return data;
    }

    public void setId(String id){ this.id = id; }
    public void setAuthor(String autor) {
        this.autor = autor;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setDate(Date data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "autor='" + autor + '\'' +
                ", text='" + text + '\'' +
                ", data=" + data +
                '}';
    }

}