package com.grupo7.ticket_system.validateTicket;

import java.util.List;
import java.util.Map;

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
        String sqltovalidateTicket= "UPDATE entrada SET id_dispositivo= ? WHERE id_entrada= ? AND id_dispositivo IS NULL";
        int updatedRows = template.update(sqltovalidateTicket,deviceId, ticketId);
        if (updatedRows == 0) {
            throw new IllegalArgumentException("Entrada invalida o ya validada");
        }
    }

    //save new String in ticket qr
    public void regenerateQr(String newQr, int ticketId){
        String sqltoregenerateQr= "UPDATE entrada SET qr_entrada=? WHERE id_entrada=?";
        int updatedRows = template.update(sqltoregenerateQr,newQr,ticketId);
        if (updatedRows == 0) {
            throw new IllegalArgumentException("Entrada invalida");
        }
    }

    public List<Map<String, Object>> findDevices() {
        String sql = "SELECT id_dispositivo FROM dispositivo ORDER BY id_dispositivo";
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> findTicketsPendingValidation() {
        String sql = """
            SELECT e.id_entrada,
                   e.id_evento,
                   ev.fecha_evento,
                   es.nombre_estadio,
                   s.letra_sector,
                   u.nombre_usuario
            FROM entrada e
            JOIN evento ev ON ev.id_evento = e.id_evento
            JOIN estadio es ON es.id_estadio = e.id_estadio
            LEFT JOIN sector s ON s.id_sector = e.id_sector
            JOIN usuario u ON u.id_usuario = e.id_usuario
            WHERE e.id_dispositivo IS NULL
            ORDER BY ev.fecha_evento DESC, e.id_entrada DESC
            """;
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> findTicketsForQrRegeneration() {
        String sql = """
            SELECT e.id_entrada,
                   e.id_evento,
                   ev.fecha_evento,
                   es.nombre_estadio,
                   s.letra_sector,
                   u.nombre_usuario,
                   e.id_dispositivo
            FROM entrada e
            JOIN evento ev ON ev.id_evento = e.id_evento
            JOIN estadio es ON es.id_estadio = e.id_estadio
            LEFT JOIN sector s ON s.id_sector = e.id_sector
            JOIN usuario u ON u.id_usuario = e.id_usuario
            ORDER BY ev.fecha_evento DESC, e.id_entrada DESC
            """;
        return template.queryForList(sql);
    }
}
