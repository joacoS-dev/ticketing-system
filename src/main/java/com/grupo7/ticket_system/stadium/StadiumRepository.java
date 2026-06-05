package com.grupo7.ticket_system.stadium;

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
public class StadiumRepository {

    @Autowired
    private JdbcTemplate template;

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

    public Section saveSection(Section section){
        String sqltosavesection= "INSERT INTO sector VALUES (?,?,?,?,?)";
        System.out.println("stadiumId: " + section.getStadiumId());
        template.update(sqltosavesection, section.getSectionId(), section.getStadiumId(), section.getMaxCapacity(), section.getPrice(), section.getSectorLetter());
        return section;
        
    }
}