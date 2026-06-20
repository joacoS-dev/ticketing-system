package com.grupo7.ticket_system.transfers;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransferRepository {
   
    private final JdbcTemplate template;

    TransferRepository(JdbcTemplate template){
        this.template= template;
    }


    //transfer ticket to another user
    public void saveTransfer(int ticketId, int newOwnerId,int ownerId){
        String sqltotransferticket= "UPDATE entrada SET id_usuario=?, cantidad_transferencias_realizadas= cantidad_transferencias_realizadas + 1 WHERE id_entrada= ?"; 
        template.update(sqltotransferticket, newOwnerId,ticketId);

        String sqltoinserttransfer= "INSERT INTO transferencia(fecha_transferencia, estado_transferencia, id_entrada, id_usuario_recibe, id_usuario_envia) VALUES(?,?,?,?,?)";
        template.update(sqltoinserttransfer,LocalDateTime.now(), true, ticketId, newOwnerId, ownerId);
    }
}
