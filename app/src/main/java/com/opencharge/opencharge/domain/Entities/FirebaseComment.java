package com.opencharge.opencharge.domain.Entities;

import java.util.Date;

/**
 * Created by Oriol on 4/5/2017.
 */

public class FirebaseComment {

    public String id;
    public String autor;
    public String text;
    public Date data;

    //Empty constructor needed for Firebase
    public FirebaseComment() {}


    public FirebaseComment(String id) {
        this.id = id;
    }

    //Getters and setters

    public String getId(){ return id; }

    public String getAutor() { return autor; }

    public String getText() {
        return text;
    }

    public Date getData() {
        return data;
    }

    public void setId(String id){ this.id = id; }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
