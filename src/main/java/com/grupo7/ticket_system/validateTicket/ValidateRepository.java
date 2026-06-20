package com.grupo7.ticket_system.validateTicket;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ValidateRepository {
    
    private final JdbcTemplate template;

    ValidateRepository(JdbcTemplate template){
        this.template= template;
    }

    //add device to ticket
    public void saveValidation(int deviceId, int ticketId){
        String sqltovalidateTicket= "UPDATE entrada SET id_dispositivo= ? WHERE id_entrada= ?";
        template.update(sqltovalidateTicket,deviceId, ticketId);

    }
}
