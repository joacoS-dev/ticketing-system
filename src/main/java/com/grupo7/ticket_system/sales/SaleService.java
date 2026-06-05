package com.grupo7.ticket_system.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;

@Service
public class SaleService {
    
    @Autowired
    SaleRepository saleRepository;

    public Sale createSale(Sale sale, List<Ticket> tickets){
        return saleRepository.saveSale(sale, tickets);
    }
}
