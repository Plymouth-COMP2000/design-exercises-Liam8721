package com.example.comp2000;
public class ReservationModels {
    private int id;
    private int PartySize;
    private String Date;
    private String AdditionalInfo;

    public ReservationModels(int id, int PartySize, String Date, String AdditionalInfo) {
        this.id = id;
        this.PartySize = PartySize;
        this.Date = Date;
        this.AdditionalInfo = AdditionalInfo;
    }

    // Getters
    public int GetId() { return id; }
    public int GetPartySize() { return PartySize; }
    public String GetDate() { return Date; }
    public String GetAdditionalInfo() { return AdditionalInfo; }

    // Setters
    public void SetId(int id) { this.id = id; }
    public void SetPartySize(int PartySize) { this.PartySize = PartySize; }
    public void SetDate(String Date) { this.Date = Date; }
    public void SetAdditionalInfo(String AdditionalInfo) { this.AdditionalInfo = AdditionalInfo; }

}

