package com.grupo7.ticket_system.events;

import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.Event;

import java.util.List;
import java.util.Map;

@Service
public class EventService {
    
    private final EventRepository eventRepository;

    EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event){
        if (event == null) {
            throw new IllegalArgumentException("Los datos del evento son obligatorios");
        }
        if (event.getEventDate() == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria");
        }
        if (event.getStadiumId() <= 0 || event.getAdminId() <= 0 || event.getLocalTeamId() <= 0 || event.getVisitorTeamId() <= 0) {
            throw new IllegalArgumentException("Estadio, admin y equipos son obligatorios");
        }
        if (event.getLocalTeamId() == event.getVisitorTeamId()) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo");
        }

        if(!eventRepository.existsByStadiumAndDateTime(event)){
            return eventRepository.saveEvent(event);
        }
        else{
             throw new IllegalArgumentException("An event already exists in the stadium at this time");
        }
    }

    public List<Map<String, Object>> getAllEvents() {
        return eventRepository.findAllEvents();
    }

    public List<Map<String, Object>> getMostSoldEvents() {
        return eventRepository.findMostSoldEvents();
    }

    public List<Map<String, Object>> getAllTeams() {
        return eventRepository.findAllTeams();
    }

    public List<Map<String, Object>> getAllAdmins() {
        return eventRepository.findAllAdmins();
    }
}
