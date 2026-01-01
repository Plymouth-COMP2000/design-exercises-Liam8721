package com.example.comp2000.database;

public class Booking {

    private int id;
    private String date;
    private String time;
    private int partySize;
    private String guestName;
    private String notes;
    private boolean isConfirmed;

    public Booking(String date, String time, int partySize, String guestName, String notes) {
        this.date = date;
        this.time = time;
        this.partySize = partySize;
        this.guestName = guestName;
        this.notes = notes;
        this.isConfirmed = false; // Bookings start as unconfirmed requests
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

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
