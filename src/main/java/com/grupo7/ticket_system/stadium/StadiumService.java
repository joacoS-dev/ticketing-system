package com.grupo7.ticket_system.stadium;

import java.util.ArrayList;
import java.util.List;

import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {
    
    @Autowired
    StadiumRepository stadiumRepository;

    public List<Stadium> getAllStadiums(){
        return stadiumRepository.getAllStadiums();
    }

    public Section saveSection(Section section){ //agregar logica de si un sector ya esta implementado etc
        return stadiumRepository.saveSection(section);
    }
}
