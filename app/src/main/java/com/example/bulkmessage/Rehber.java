package com.example.bulkmessage;

public class Rehber {
    private String name, number, image;

    public Rehber(String name, String number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
