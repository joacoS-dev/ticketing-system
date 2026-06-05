package com.grupo7.ticket_system.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;

@Repository
public class SaleRepository {
    
    @Autowired
    JdbcTemplate template;
    
    public Sale saveSale(Sale sale, List<Ticket> tickets){
        String sqltosavesale= "INSERT INTO venta(fecha_venta, estado, monto_total, porcentaje_comision_aplicado, id_usuario, id_tasa_comision) VALUES(?,?,?,?,?,?)";
        template.update(sqltosavesale, sale.getSaleDate(), sale.getState(), sale.getTotalSalePrice(), sale.getComissionRate(),
                        sale.getUserId(), sale.getComissionRateId());
        /*String sqltosaveticket="INSERT INTO entrada(cantidad_transferencias_realizadas, id_sector, id_estadio,id_venta, id_dispositivo, id_evento, id_usuario) VALUES(?,?,?,?,?,?,?)";
        for(Ticket ticket:tickets){
            template.update(sqltosaveticket, ticket.getTransfersMade(), ticket.getSectionId(),ticket.getStadiumId(), ticket.getSaleId(), 
                            ticket.getDeviceId(), ticket.getEventId(), ticket.getUserId());
        }   */
        return sale;
    }
}
