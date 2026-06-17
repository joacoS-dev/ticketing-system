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
        String sqltosaveevent= "INSERT INTO evento(fecha_evento,id_estadio,id_usuario,id_equipo_local,id_equipo_visitante) VALUES (?,?,?,?,?)";
        template.update(sqltosaveevent,event.getEventDate(), event.getStadiumId(), event.getUserId(), event.getLocalTeamId(), 
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
}
