package com.grupo7.ticket_system.events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.grupo7.ticket_system.models.Event;

@Repository
public class EventRepository {
    
    private final JdbcTemplate template;

    EventRepository(JdbcTemplate template) {
        this.template = template;
    }
    //save event
    public Event saveEvent(Event event){
        String sqltosaveevent= "INSERT INTO evento(fecha_evento,id_estadio,id_admin,id_equipo_local,id_equipo_visitante) VALUES (?,?,?,?,?)";
        template.update(sqltosaveevent,event.getEventDate(), event.getStadiumId(), event.getAdminId(), event.getLocalTeamId(), 
                        event.getVisitorTeamId());
        
        String sqltogeteventid= "SELECT LAST_INSERT_ID()";        
        event.setEventId(template.queryForObject(sqltogeteventid, int.class));
        return event;
    }
    //check if an event already exists in this stadium at this datetime.
    public boolean existsByStadiumAndDateTime(Event event){
        String sqltofindbystadiumanddatetime= "SELECT COUNT(*) FROM evento WHERE id_estadio = ? AND fecha_evento BETWEEN DATE_SUB(?, INTERVAL 3 HOUR) AND DATE_ADD(?, INTERVAL 3 HOUR)";
        return template.queryForObject(sqltofindbystadiumanddatetime, int.class, event.getStadiumId(), event.getEventDate(),event.getEventDate()) >0;
    }

       public List<Map<String, Object>> findAllEvents() {
        String sql = """
            SELECT ev.id_evento,
                   ev.fecha_evento,
                   es.nombre_estadio,
                   ev.id_equipo_local,
                   ev.id_equipo_visitante
            FROM evento ev
            JOIN estadio es ON es.id_estadio = ev.id_estadio
            ORDER BY ev.fecha_evento DESC
            """;
        return template.queryForList(sql);
    }

    public List<Map<String, Object>> findMostSoldEvents() {
        String sql = """
            SELECT ev.id_evento,
                   ev.fecha_evento,
                   es.nombre_estadio,
                   COUNT(en.id_entrada) AS entradas_vendidas
            FROM evento ev
            JOIN estadio es ON es.id_estadio = ev.id_estadio
            LEFT JOIN entrada en ON en.id_evento = ev.id_evento
            GROUP BY ev.id_evento, ev.fecha_evento, es.nombre_estadio
            ORDER BY entradas_vendidas DESC, ev.fecha_evento DESC
            LIMIT 10
            """;
        return template.queryForList(sql);
    }
}
