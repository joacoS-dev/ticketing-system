package com.grupo7.ticket_system.sales;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;


@Repository
public class SaleRepository {
    
    @Autowired
    JdbcTemplate template;
    
    public Sale saveSale(Sale sale){
        String sqltosavesale= "INSERT INTO venta(estado, monto_total, porcentaje_comision_aplicado, id_usuario, id_tasa_comision) VALUES(?,?,?,?,?)";
        template.update(sqltosavesale,sale.getState(), sale.getTotalSalePrice(), sale.getComissionRate(),
                        sale.getUserId(), sale.getComissionRateId());
        
        String sqltogetsaleid= "SELECT LAST_INSERT_ID()";
        int generatedId= template.queryForObject(sqltogetsaleid,int.class);
        sale.setSaleId(generatedId);
        sale.setSaleDate(LocalDateTime.now());
        return sale;
    }

    public void saveTicket(List<Ticket> tickets){
        String sqltosaveticket="INSERT INTO entrada(cantidad_transferencias_realizadas, id_sector, id_estadio,id_venta, id_evento, id_usuario) VALUES(?,?,?,?,?,?)";
        for(Ticket ticket:tickets){
            template.update(sqltosaveticket, ticket.getTransfersMade(), ticket.getSectionId(),ticket.getStadiumId(), ticket.getSaleId(), 
                            ticket.getEventId(), ticket.getUserId());
        }  
    } 
}
