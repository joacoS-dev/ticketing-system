package com.grupo7.ticket_system.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {
    
    @Autowired
    InfrastructureRepository stadiumRepository;

    public List<Stadium> getAllStadiums(){
        if(stadiumRepository.getAllStadiums() != null){
            return stadiumRepository.getAllStadiums();
        }else{
            throw new IllegalArgumentException("No stadiums registered");
        }
    }

    public Section createSection(Section section){ //the stadium id this method accept is any stadium, i need that one to be choosed by the admin.
        if(!stadiumRepository.existsByStadiumAndLetter(section)){
            return stadiumRepository.saveSection(section);
        }else{
            throw new IllegalArgumentException("Section already created in the stadium");
        }
    }
}
