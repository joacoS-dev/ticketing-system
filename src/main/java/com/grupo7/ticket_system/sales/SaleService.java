package com.grupo7.ticket_system.sales;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.grupo7.ticket_system.models.RequestSale;
import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;
import com.grupo7.ticket_system.users.UserRepository;

@Service
public class SaleService {

    private static final double DEFAULT_COMMISSION_RATE = 0.05;
    private static final int DEFAULT_COMMISSION_RATE_ID = 1;
    
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    SaleService(SaleRepository saleRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.userRepository= userRepository;
    }

    public Sale createSale(int eventId, RequestSale requestSale) {
        if (requestSale == null) {
            throw new IllegalArgumentException("Sale data is required");
        }
        if (requestSale.getTickets() == null || requestSale.getTickets().isEmpty() || requestSale.getTickets().size() > 5) {
            throw new IllegalArgumentException("The sale must contain at least one ticket and at most 5 tickets");
        }
        int stadiumId = saleRepository.findStadiumIdByEventId(eventId);
        String sectionId = requestSale.getTickets().get(0).getSectionId();
        int sectionPrice = saleRepository.findSectionPrice(sectionId, stadiumId);

        for (Ticket ticket:requestSale.getTickets()) {
            if (!sectionId.equals(ticket.getSectionId())) {
                throw new IllegalArgumentException("All tickets must belong to the selected section");
            }
        }

        Sale savedSale = new Sale();
        savedSale.setState(true);
        savedSale.setSaleDate(LocalDateTime.now());
        savedSale.setTotalSalePrice((int) Math.round(sectionPrice * requestSale.getTickets().size() * (1 + DEFAULT_COMMISSION_RATE)));
        savedSale.setComissionRate(DEFAULT_COMMISSION_RATE);
        savedSale.setComissionRateId(DEFAULT_COMMISSION_RATE_ID);
        savedSale.setUserId(userRepository.getUserIdByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ));
        saleRepository.saveSale(savedSale);
        for (Ticket ticket:requestSale.getTickets()) {
            ticket.setSaleId(savedSale.getSaleId());
            ticket.setUserId(savedSale.getUserId());
            ticket.setQrToken(UUID.randomUUID().toString());
            ticket.setEventId(eventId);
            ticket.setStadiumId(stadiumId);
        }
        saleRepository.saveTicket(requestSale.getTickets());
        return savedSale;
    }

    public List<Map<String, Object>> getEventsForSale() {
        return saleRepository.findEventsForSale();
    }

    public List<Map<String, Object>> getSectionsByStadiumId(int stadiumId) {
        return saleRepository.findSectionsByStadiumId(stadiumId);
    }
}
