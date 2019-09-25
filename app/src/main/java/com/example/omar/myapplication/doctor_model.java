package com.example.omar.myapplication;

public class doctor_model
{
    String names,id,dices;

    public doctor_model(String names, String age, String dices) {
        this.names = names;
        this.id = age;
        this.dices = dices;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAge() {
        return id;
    }

    public void setAge(String age) {
        this.id = age;
    }

    public String getDices() {
        return dices;
    }

    public void setDices(String dices) {
        this.dices = dices;
    }
}
