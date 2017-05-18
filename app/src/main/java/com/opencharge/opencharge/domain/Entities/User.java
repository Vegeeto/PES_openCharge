package com.opencharge.opencharge.domain.Entities;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/05/2017.
 */

public class User {
    private String name;
    private String photo;
    private String password;
    private String email;
    private ArrayList<Point> puntsCreats;
    private ArrayList<Point> puntsReservats;

    User (String name, String photo, String password, String email, ArrayList<Point> puntsCreats, ArrayList<Point> puntsReservats) {
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.email = email;
        this.puntsCreats = puntsCreats;
        this.puntsReservats = puntsReservats;
    }

    public String getName () {
        return name;
    }

    public String getPhoto () {
        return photo;
    }
    public String getPassword () {
        return password;
    }
    public String getEmail () {
        return email;
    }

    public ArrayList<Point> getPuntsCreats() {
        return puntsCreats;
    }

    public ArrayList<Point> getPuntsReservats() {
        return puntsReservats;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setPhoto (String photo) {
        this.photo = photo;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setPuntsCreats(ArrayList<Point> puntsCreats) {
        this.puntsCreats = puntsCreats;
    }

    public void setPuntsReservats(ArrayList<Point> puntsReservats) {
        this.puntsReservats = puntsReservats;
    }

    public void addPointCreat(Point p) {
        puntsCreats.add(p);
    }

    public void addPointReservat(Point p) {
        puntsReservats.add(p);
    }
}
