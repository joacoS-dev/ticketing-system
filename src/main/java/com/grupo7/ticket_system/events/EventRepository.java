package com.grupo7.ticket_system.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.grupo7.ticket_system.models.Event;

@Repository
public class EventRepository {
    
    @Autowired
    private JdbcTemplate template;

    //save event
    public Event saveEvent(Event event){
        String sqltosaveevent= "INSERT INTO evento(fecha_evento,id_estadio,id_usuario,id_equipo_local,id_equipo_visitante) VALUES (?,?,?,?,?)";
        template.update(sqltosaveevent,event.getEventDate(), event.getStadiumId(), event.getUserId(), event.getLocalTeamId(), 
                        event.getVisitorTeamId());
        return event;
    }

    //check if an event already exists in this stadium at this datetime.
    public boolean existsByStadiumAndDateTime(Event event){
        String sqltofindbystadiumanddatetime= "SELECT COUNT(*) FROM evento WHERE id_estadio = ? AND fecha_evento BETWEEN DATE_ADD(?, INTERVAL 6 HOUR)";
        return template.queryForObject(sqltofindbystadiumanddatetime, int.class, event.getStadiumId(), event.getEventDate()) >0;
    }
}
