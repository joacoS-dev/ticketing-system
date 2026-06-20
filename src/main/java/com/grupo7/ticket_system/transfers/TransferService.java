package com.grupo7.ticket_system.transfers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.grupo7.ticket_system.models.Ticket;
import com.grupo7.ticket_system.sales.SaleRepository;
import com.grupo7.ticket_system.users.UserRepository;

@Service
public class TransferService {
    
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    TransferService(TransferRepository transferRepository, UserRepository userRepository, SaleRepository saleRepository){
        this.transferRepository= transferRepository;
        this.userRepository= userRepository;
        this.saleRepository= saleRepository;
    }

    public void transferTicket(int ticketId, String newOwnerUsername, String newOwnerEmail){
        if(userRepository.existsByMail(newOwnerEmail) &&  saleRepository.ticketCanBeTransfered(ticketId)){
            transferRepository.saveTransfer(ticketId, userRepository.getUserIdByUsername(newOwnerUsername),
                                            userRepository.getUserIdByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        }else{
            throw new IllegalArgumentException("This user doesn't exist or the ticket already was transfer 3 times");
        }
    }
}
