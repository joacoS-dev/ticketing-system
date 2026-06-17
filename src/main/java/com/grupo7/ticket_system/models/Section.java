package com.grupo7.ticket_system.models;

public class Section {
    
    int sectionId;
    int stadiumId;
    int maxCapacity;
    int price;
    String sectionLetter;

    public int getSectionId() {
        return sectionId;
    }
    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    
    public int getStadiumId() {
        return stadiumId;
    }
    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getSectionLetter() {
        return sectionLetter;
    }
    public void setSectionLetter(String sectorLetter) {
        this.sectionLetter = sectorLetter;
    }
    @Override
    public String toString() {
        return "Section [sectorId=" + sectionId + ", stadiumId=" + stadiumId + ", maxCapacity=" + maxCapacity
                + ", price=" + price + ", sectorLetter=" + sectionLetter + "]";
    }
}
