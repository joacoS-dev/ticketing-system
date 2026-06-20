package com.grupo7.ticket_system.validateTicket;

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
}
