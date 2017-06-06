package com.opencharge.opencharge.domain.Entities;

/**
 * Created by DmnT on 26/04/2017.
 */

public class Comment {

    public String id;
    public String user;
    public String text;
    public String date;

    //Empty constructor needed for Firebase
    public Comment() {}

    public Comment(String id) {
        this.id = id;
    }

    public Comment(String user, String text, String date) {
        this.user = user;
        this.text = text;
        this.date = date;
    }

    //Getters and setters


    public String getId(){ return id; }
    public String getUser() { return user; }
    public String getText() {
        return text;
    }
    public String getDate() { return date; }

    public void setId(String id){ this.id = id; }
    public void setUser(String user) {
        this.user = user;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setData(long data) {
        this.date = String.valueOf(data);
    }


    @Override
    public String toString() {
        return "Comment{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }

}