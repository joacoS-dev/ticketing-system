package com.grupo7.ticket_system.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.ticket_system.models.Event;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/event")
public class EventController {
    
    @Autowired
    EventService eventService;

    @PostMapping("/createEvent")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
}
