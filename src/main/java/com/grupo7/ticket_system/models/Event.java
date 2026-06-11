package com.grupo7.ticket_system.models;
import java.time.LocalDateTime;

public class Event {

    int eventId;
    LocalDateTime eventDate;
    int stadiumId;
    int userId;
    int localTeamId;
    int visitorTeamId;
    
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public LocalDateTime getEventDate() {
        return eventDate;
    }
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
    public int getStadiumId() {
        return stadiumId;
    }
    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getLocalTeamId() {
        return localTeamId;
    }
    public void setLocalTeamId(int localTeamId) {
        this.localTeamId = localTeamId;
    }
    public int getVisitorTeamId() {
        return visitorTeamId;
    }
    public void setVisitorTeamId(int visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }
    
    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", eventDate=" + eventDate + ", stadiumId=" + stadiumId + ", userId="
                + userId + ", localTeamId=" + localTeamId + ", visitor=" + visitorTeamId + "]";
    }
}
