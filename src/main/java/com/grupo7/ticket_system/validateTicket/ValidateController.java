package com.grupo7.ticket_system.validateTicket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/validates")
public class ValidateController {
    
    private final ValidateService validateService;

    ValidateController(ValidateService validateService){
        this.validateService= validateService;
    }

    @PostMapping("{deviceId}/{ticketId}/validateTicket")
    public void validateTicket(@PathVariable int deviceId, @PathVariable int ticketId) {
       validateService.validateTicket(deviceId, ticketId);
    }

    @PostMapping("{ticketId}/regenerateQr")
    public void regenerateTicketQr(@RequestBody int ticketId) {    
        validateService.regenerateQr(ticketId);
    }
}
