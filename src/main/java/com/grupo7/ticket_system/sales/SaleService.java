package com.grupo7.ticket_system.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.ticket_system.models.RequestSale;
import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;


@Service
public class SaleService {
    
    @Autowired
    SaleRepository saleRepository;

    public Sale createSale(RequestSale requestSale){ 
        for(Ticket ticket:requestSale.getTickets()){
            ticket.setSaleId(666); //set a valid saleId (take from last saleid created database)
            ticket.setTransfersMade(0);
            ticket.setUserId(666);//set a valid userId(take from web session)
            ticket.setEventId(666); //set a valid eventId (take from database)
        }
        saleRepository.saveTicket(requestSale.getTickets()); 
        return saleRepository.saveSale(requestSale.getSale()); //set a valid userId; set a valid id_tasa_comision.
    }
}