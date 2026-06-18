package com.grupo7.ticket_system.sales;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.RequestSale;
import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;
import com.grupo7.ticket_system.users.UserRepository;

@Service
public class SaleService {
    
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    SaleService(SaleRepository saleRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.userRepository= userRepository;
    }

    public Sale createSale(int eventId, RequestSale requestSale){ 
        Sale savedSale= requestSale.getSale();
        savedSale.setSaleDate(LocalDateTime.now());
        savedSale.setUserId(userRepository.getUserIdByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        saleRepository.saveSale(savedSale);
        if(requestSale.getTickets().size() <= 5){
            for(Ticket ticket:requestSale.getTickets()){
                ticket.setSaleId(savedSale.getSaleId()); 
                ticket.setUserId(savedSale.getUserId());
                ticket.setQrToken(UUID.randomUUID().toString());
                ticket.setEventId(eventId);
            }
            saleRepository.saveTicket(requestSale.getTickets()); 
        }else{
            throw new IllegalArgumentException("You can buy at most 5 tickets per purchase");
        }
        return savedSale;
    }
}