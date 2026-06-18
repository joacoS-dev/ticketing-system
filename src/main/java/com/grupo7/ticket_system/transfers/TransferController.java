package com.grupo7.ticket_system.transfers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo7.ticket_system.models.Ticket;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transfers")
public class TransferController {
    
    private final TransferService transferService;

    TransferController(TransferService transferService){
        this.transferService= transferService;
    }

    @PostMapping("/{ticketId}/transferTicket")
    public void transferTicket(@PathVariable int ticketId,@RequestBody Map<String,String> body) {
        transferService.transferTicket(ticketId, body.get("newOwnerUsername"), body.get("newOwnerEmail"));
    }
}
