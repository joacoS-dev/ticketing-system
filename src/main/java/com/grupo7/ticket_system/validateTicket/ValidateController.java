package com.grupo7.ticket_system.validateTicket;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/validates")
public class ValidateController {

    private final ValidateService validateService;

    ValidateController(ValidateService validateService){
        this.validateService= validateService;
    }

    @PostMapping("{deviceId}/{ticketId}/validateTicket")
    public void validateTicket(@PathVariable int deviceId, @PathVariable int ticketId){
        validateService.validateTicket(deviceId, ticketId);
    }

    @PostMapping("{ticketId}/regenerateQr")
    public void regenerateTicketQr(@PathVariable int ticketId){ // El endpoint ya tenia el ticketId en la ruta, pero el metodo lo estaba esperando en el body. Es @PathVariable para que tome correctamente el ID desde /validates/{ticketId}/regenerateQr
        validateService.regenerateQr(ticketId);
    }

    @PostMapping("/devices/{deviceId}/assign-funcionario/{userId}")
    public String assignDeviceToFuncionario(@PathVariable int deviceId, @PathVariable int userId) {
        validateService.assignDeviceToFuncionario(deviceId, userId);
        return "dispositivoAgregado";
    }

    @GetMapping("/devices")
    public List<Map<String, Object>> getDevices() {
        return validateService.getDevices();
    }

    @GetMapping("/tickets/pending")
    public List<Map<String, Object>> getTicketsPendingValidation() {
        return validateService.getTicketsPendingValidation();
    }

    @GetMapping("/tickets")
    public List<Map<String, Object>> getTicketsForQrRegeneration() {
        return validateService.getTicketsForQrRegeneration();
    }
}
