package com.example.omar.myapplication;


public class Message {
    private String id ;
    private String from_id ;
    private String to_id ;
    private String text ;
    private String sentat ;


    public Message (String text , String sentat , String from_id){
        this.text = text ;
        this.sentat = sentat ;
        this.from_id = from_id;
    }
    public String getFrom_id() {
        return from_id;
    }

    public String getId() {
        return id;
    }

    public String getSentat() {
        return sentat;
    }

    public String getText() {
        return text;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSentat(String sentat) {
        this.sentat = sentat;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

}
