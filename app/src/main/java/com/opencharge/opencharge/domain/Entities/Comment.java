package com.opencharge.opencharge.domain.Entities;

import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;

import java.util.Date;

/**
 * Created by DmnT on 26/04/2017.
 */

public class Comment {

    public String id;
    public String autor;
    public String text;
    public String data;

    //Empty constructor needed for Firebase
    public Comment() {}

    public Comment(String id) {
        this.id = id;
    }

    public Comment(String autor, String text, String data) {
        this.autor = autor;
        this.text = text;
        this.data = data;
    }

    //Getters and setters


    public String getId(){ return id; }
    public String getAutor() { return autor; }
    public String getText() {
        return text;
    }
    public String getData() { return data; }

    public void setId(String id){ this.id = id; }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setData(long data) {
        this.data = String.valueOf(data);
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