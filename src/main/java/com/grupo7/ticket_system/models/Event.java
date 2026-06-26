package com.grupo7.ticket_system.models;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Event {

    int eventId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime eventDate;
    int stadiumId;
    int adminId;
    int localTeamId;
    int visitorTeamId;
    
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public int getUserId() {
        return adminId;
    }
    public void setUserId(int userId) {
        this.adminId = userId;
    }

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
                + adminId + ", localTeamId=" + localTeamId + ", visitor=" + visitorTeamId + "]";
    }
}
