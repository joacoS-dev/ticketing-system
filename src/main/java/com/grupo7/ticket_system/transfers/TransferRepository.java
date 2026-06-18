package com.grupo7.ticket_system.transfers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepository {
   
    private final JdbcTemplate template;

    TransferRepository(JdbcTemplate template){
        this.template= template;
    }


    //transfer ticket to another user
    public void saveTransfer(int ticketId, int newOwnerId){
        String sqltotransferticket= "UPDATE entrada SET id_usuario=?, cantidad_transferencias_realizadas= cantidad_transferencias_realizadas + 1 WHERE id_entrada= ?"; 
        template.update(sqltotransferticket, newOwnerId,ticketId);
    }
}
