package com.grupo7.ticket_system.validateTicket;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ValidateService {
    
    private final ValidateRepository validateRepository;
    
    ValidateService(ValidateRepository validateRepository) {
        this.validateRepository = validateRepository;
    }

    public void validateTicket(int deviceId,int ticketId){
        validateRepository.saveValidation(deviceId, ticketId);
    }

    public void regenerateQr(int ticketId){
        String newQr= UUID.randomUUID().toString();
        validateRepository.regenerateQr(newQr,ticketId);
    }
}
