package com.grupo7.ticket_system.sales;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<Map<String, Object>> findEventsForSale() {
        String sql = """
            SELECT ev.id_evento,
                   ev.fecha_evento,
                   ev.id_estadio,
                   es.nombre_estadio
            FROM evento ev
            JOIN estadio es ON es.id_estadio = ev.id_estadio
            ORDER BY ev.fecha_evento DESC
            """;
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> findSectionsByStadiumId(int stadiumId) {
        String sql = """
            SELECT id_sector,
                   letra_sector,
                   costo,
                   capacidad_maxima
            FROM sector
            WHERE id_estadio = ?
            ORDER BY letra_sector
            """;
        return template.queryForList(sql, stadiumId);
    }

    public int findStadiumIdByEventId(int eventId) {
        String sql = "SELECT id_estadio FROM evento WHERE id_evento = ?";
        try {
            return template.queryForObject(sql, int.class, eventId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Evento invalido");
        }
    }

    public int findSectionPrice(String sectionId, int stadiumId) {
        String sql = "SELECT costo FROM sector WHERE id_sector = ? AND id_estadio = ?";
        try {
            return template.queryForObject(sql, int.class, sectionId, stadiumId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Sector invalido para el estadio seleccionado");
        }
    }

    public boolean ticketCanBeTransfered(int idTicket){
        String sqltogettransfersnum= "SELECT cantidad_transferencias_realizadas FROM entrada WHERE id_entrada=?";
        int transfersNum= template.queryForObject(sqltogettransfersnum, int.class, idTicket);

        String sqltogetdeviceId= "SELECT id_dispositivo FROM entrada WHERE id_entrada= ?";
        Integer deviceExists= template.queryForObject(sqltogetdeviceId, Integer.class, idTicket);

        if(transfersNum <3 && deviceExists == null){
            return true;
        }else{
            return false;
        }
    }
}
