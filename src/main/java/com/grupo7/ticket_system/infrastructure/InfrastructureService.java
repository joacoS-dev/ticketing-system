package com.grupo7.ticket_system.infrastructure;
import java.util.ArrayList;
import java.util.List;
import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {
    
    private final InfrastructureRepository infrastructureRepository;

    InfrastructureService(InfrastructureRepository infrastructureRepository) {
        this.infrastructureRepository = infrastructureRepository;
    }

    public Section createSection(Section section, int stadiumId){ 
        if(!infrastructureRepository.existsByStadiumAndLetter(section)){
            section.setStadiumId(stadiumId);
            return infrastructureRepository.saveSection(section);
        }else{
            throw new IllegalArgumentException("Section already created in the stadium");
        }
    }

    public List<Stadium> getAllStadiums(){
        if(infrastructureRepository.getAllStadiums() != null){
            return infrastructureRepository.getAllStadiums();
        }else{
            throw new IllegalArgumentException("No stadiums registered");
        }
    }
}
