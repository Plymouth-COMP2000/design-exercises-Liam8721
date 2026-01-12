package com.example.comp2000.database;

public class Booking {

    private int id;
    private String date;
    private String time;
    private int partySize;
    private String guestName;
    private String notes;
    private String guestUsername;
    private String guestEmail;
    public Booking(String date, String time, int partySize, String guestName, String guestUsername, String guestEmail, String notes) {
        this.date = date;
        this.time = time;
        this.partySize = partySize;
        this.guestName = guestName;
        this.notes = notes;
        this.guestUsername = guestUsername;
        this.guestEmail = guestEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }
}
