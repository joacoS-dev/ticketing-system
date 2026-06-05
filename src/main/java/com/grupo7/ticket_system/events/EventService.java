package com.grupo7.ticket_system.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.ticket_system.models.Event;

@Service
public class EventService {
    
    @Autowired
    EventRepository eventRepository;

    public Event createEvent(Event event){
        return eventRepository.saveEvent(event);
    }
}
