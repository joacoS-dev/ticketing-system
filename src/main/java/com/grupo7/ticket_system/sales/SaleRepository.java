package com.grupo7.ticket_system.sales;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.grupo7.ticket_system.models.Sale;
import com.grupo7.ticket_system.models.Ticket;

@Repository
public class SaleRepository {
    
    private final JdbcTemplate template;

    SaleRepository(JdbcTemplate template) {
        this.template = template;
    }
    //save sale
    public Sale saveSale(Sale sale){
        String sqltosavesale= "INSERT INTO venta(estado, fecha_venta, monto_total, porcentaje_comision_aplicado, id_usuario, id_tasa_comision) VALUES(?,?,?,?,?,?)";
        template.update(sqltosavesale,sale.getState(),sale.getSaleDate(), sale.getTotalSalePrice(), sale.getComissionRate(),
                        sale.getUserId(), sale.getComissionRateId());
        
        String sqltogetsaleid= "SELECT LAST_INSERT_ID()";
        sale.setSaleId(template.queryForObject(sqltogetsaleid,int.class));
        return sale;
    }
    //save ticket
    public void saveTicket(List<Ticket> tickets){
        String sqltosaveticket="INSERT INTO entrada(cantidad_transferencias_realizadas,qr_entrada, id_sector, id_estadio,id_venta, id_evento, id_usuario) VALUES(?,?,?,?,?,?,?)";
        for(Ticket ticket:tickets){
            template.update(sqltosaveticket, ticket.getTransfersMade(), ticket.getQrToken(),ticket.getSectionId(),ticket.getStadiumId(), ticket.getSaleId(), 
                            ticket.getEventId(), ticket.getUserId());
        }  
    }

    public boolean ticketCanBeTransfered(int idTicket){
        String sqltogettransfersnum= "SELECT cantidad_transferencias_realizadas FROM entrada WHERE id_entrada=?";
        return template.queryForObject(sqltogettransfersnum, int.class, idTicket) < 3;
    }
}
