package com.grupo7.ticket_system.infrastructure;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;

@Repository
public class InfrastructureRepository {

    @Autowired
    private JdbcTemplate template;

    //get all stadiums
    public List<Stadium> getAllStadiums(){
        String sqltogetallstadiums= "SELECT * FROM estadio";
        return template.query(sqltogetallstadiums, (rs, rowNum) -> {
        Stadium stadium = new Stadium();
        stadium.setStadiumId(rs.getInt("id_estadio"));
        stadium.setStadiumName(rs.getString("nombre_estadio"));
        stadium.setCountryId(rs.getInt("id_pais"));
        return stadium;
        });
    }

    //save section
    public Section saveSection(Section section){//id_estadio no es id del estadio real (ver como manejarlo, el admin entra a x estadio y ahi dentor puede crear sectores, entonces el id estdio ya es fijo, x parametro?)
        String sqltosavesection= "INSERT INTO sector(id_sector, id_estadio, capacidad_maxima, costo, letra_sector) VALUES (?,?,?,?,?)";
        template.update(sqltosavesection, section.getSectionId(), section.getStadiumId(), section.getMaxCapacity(), 
                        section.getPrice(), section.getSectionLetter());
        return section;
    }

    // check if the section already exists
    public boolean existsByStadiumAndLetter(Section section){
        String sqltofindbystadiumandletter= "SELECT COUNT(*) FROM sector WHERE id_estadio= ? AND letra_sector= ?";
        return template.queryForObject(sqltofindbystadiumandletter, int.class,section.getStadiumId(), section.getSectionLetter()) > 0;
    }
}