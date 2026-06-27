package com.grupo7.ticket_system.validateTicket;

import java.util.List;
import java.util.Map;
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

    public int regenerateAllQr(){
        List<Integer> ticketIds = validateRepository.findAllTicketIds();
        for (Integer ticketId : ticketIds) {
            validateRepository.regenerateQr(UUID.randomUUID().toString(), ticketId);
        }
        return ticketIds.size();
    }

    public void assignDeviceToFuncionario(int deviceId, int userId) {
        validateRepository.assignDeviceToFuncionario(deviceId, userId);
    }

    public List<Map<String, Object>> getDevices() {
        return validateRepository.findDevices();
    }

    public List<Map<String, Object>> getTicketsPendingValidation() {
        return validateRepository.findTicketsPendingValidation();
    }

    public List<Map<String, Object>> getTicketsForQrRegeneration() {
        return validateRepository.findTicketsForQrRegeneration();
    }
}
