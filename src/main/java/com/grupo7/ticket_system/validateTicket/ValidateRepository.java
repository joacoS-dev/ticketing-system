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

    //save new String in ticket qr
    public void regenerateQr(String newQr, int ticketId){
        String sqltoregenerateQr= "UPDATE entrada SET qr_entrada=? WHERE id_entrada=?";
        template.update(sqltoregenerateQr,newQr,ticketId);
    }
}
