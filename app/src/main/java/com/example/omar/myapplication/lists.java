package com.example.omar.myapplication;

public class lists {
    String name, state;
    int img;

    lists(String name, String state, int img) {
        this.name = name;
        this.state = state;
        this.img = img;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
