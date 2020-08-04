package com.example.novalists;

public class User {

    private String Name;
    private String Email;
    private String Contact;
    private Integer PersonalList;
    private Integer SentList;
    private Integer ReceivedList;
    private String UserID;

    public User() {
    }

    public User(String contact, String email, String name, Integer personalList, Integer sentList, Integer receivedList, String userID) {
        Name = name;
        Email = email;
        Contact = contact;
        PersonalList = personalList;
        SentList = sentList;
        ReceivedList = receivedList;
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Integer getPersonalList() {
        return PersonalList;
    }

    public void setPersonalList(Integer personalList) {
        PersonalList = personalList;
    }

    public Integer getSentList() {
        return SentList;
    }

    public void setSentList(Integer sentList) {
        SentList = sentList;
    }

    public Integer getReceivedList() {
        return ReceivedList;
    }

    public void setReceivedList(Integer receivedList) {
        ReceivedList = receivedList;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
