package com.example.omar.myapplication;

public class modelPramdic {
    String names, id, dices;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDices() {
        return dices;
    }

    public void setDices(String dices) {
        this.dices = dices;
    }

    public modelPramdic(String names, String age, String dices) {
        this.names = names;
        this.id = age;
        this.dices = dices;

    }
}