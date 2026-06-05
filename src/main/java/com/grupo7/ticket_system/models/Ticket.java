package com.grupo7.ticket_system.models;

public class Ticket {
    int ticketId;
    int transfersMade;
    String sectionId;
    int stadiumId;
    int saleId;
    int deviceId;
    int eventId;
    int userId;
    
    public int getTicketId() {
        return ticketId;
    }
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    public int getTransfersMade() {
        return transfersMade;
    }
    public void setTransfersMade(int transfersMade) {
        this.transfersMade = transfersMade;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public int getStadiumId() {
        return stadiumId;
    }
    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }
    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int sellId) {
        this.saleId = sellId;
    }
    public int getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", transfersMade=" + transfersMade + ", sectionId=" + sectionId
                + ", stadiumId=" + stadiumId + ", sellId=" + saleId + ", deviceId=" + deviceId + ", eventId=" + eventId
                + ", userId=" + userId + "]";
    }
    
}
