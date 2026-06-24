package com.grupo7.ticket_system.events;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.Event;
import com.grupo7.ticket_system.users.UserRepository;

import java.util.List;
import java.util.Map;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Event createEvent(Event event){
        if (event == null) {
            throw new IllegalArgumentException("Los datos del evento son obligatorios");
        }
        if (event.getEventDate() == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria");
        }
        if (event.getStadiumId() <= 0 || event.getLocalTeamId() <= 0 || event.getVisitorTeamId() <= 0) {
            throw new IllegalArgumentException("Estadio y equipos son obligatorios");
        }
        if (event.getLocalTeamId() == event.getVisitorTeamId()) {
            throw new IllegalArgumentException("El equipo local y visitante no pueden ser el mismo");
        }
        event.setAdminId(userRepository.getUserIdByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ));

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
}
