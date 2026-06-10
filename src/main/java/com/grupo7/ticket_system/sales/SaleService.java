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
        
        Sale savedSale= saleRepository.saveSale(requestSale.getSale());

        for(Ticket ticket:requestSale.getTickets()){
                ticket.setSaleId(savedSale.getSaleId()); 
                ticket.setUserId(savedSale.getUserId());
                //ticket.setEventId(666); //set a valid eventId (take from database)?¿?¿?¿?¿?¿?
            }
            saleRepository.saveTicket(requestSale.getTickets()); 
            
        return savedSale;
    }
}