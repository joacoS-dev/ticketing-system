package com.grupo7.ticket_system.sales;
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

    public Sale createSale(RequestSale requestSale, int eventId){ 
        Sale savedSale= requestSale.getSale();
        savedSale.setUserId(userRepository.findUserIdByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        saleRepository.saveSale(savedSale);
        for(Ticket ticket:requestSale.getTickets()){
                ticket.setSaleId(savedSale.getSaleId()); 
                ticket.setUserId(savedSale.getUserId());
                ticket.setQrToken(UUID.randomUUID().toString());
                ticket.setEventId(eventId);
            }
            saleRepository.saveTicket(requestSale.getTickets()); 
        return savedSale;
    }
}