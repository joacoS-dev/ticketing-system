package com.grupo7.ticket_system.models;

public class Stadium {
    int stadiumId;
    String stadiumName;
    int countryId;
    
    public int getStadiumId() {
        return stadiumId;
    }
    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }
    public String getStadiumName() {
        return stadiumName;
    }
    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
    public int getCountryId() {
        return countryId;
    }
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    @Override
    public String toString() {
        return "Stadium [stadiumId=" + stadiumId + ", stadiumName=" + stadiumName + ", countryId=" + countryId + "]";
    }
}
