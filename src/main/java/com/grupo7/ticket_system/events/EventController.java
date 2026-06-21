package com.grupo7.ticket_system.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupo7.ticket_system.models.Event;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/events")
public class EventController {
    
    private final EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/createEvent")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/list")
    public List<Map<String, Object>> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/rankings/most-sold")
    public List<Map<String, Object>> getMostSoldEvents() {
        return eventService.getMostSoldEvents();
    }
}
