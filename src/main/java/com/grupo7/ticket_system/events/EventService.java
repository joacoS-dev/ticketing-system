package com.grupo7.ticket_system.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.Event;

@Service
public class EventService {
    
    private final EventRepository eventRepository;

    EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event){
        if(!eventRepository.existsByStadiumAndDateTime(event)){
            return eventRepository.saveEvent(event);
        }
        else{
             throw new IllegalArgumentException("An event already exists in the stadium at this time");
        }
    }
}
