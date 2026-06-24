package com.grupo7.ticket_system.infrastructure;
import java.util.List;
import java.util.Map;
import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureService {
    
    private final InfrastructureRepository infrastructureRepository;

    InfrastructureService(InfrastructureRepository infrastructureRepository) {
        this.infrastructureRepository = infrastructureRepository;
    }

    public Section createSection(Section section, int stadiumId){ 
        section.setStadiumId(stadiumId);
        if (section.getSectionLetter() != null) {
            section.setSectionLetter(section.getSectionLetter().trim().toUpperCase());
        }
        validateSection(section);
        if(!infrastructureRepository.existsByStadiumAndLetter(section)){
            return infrastructureRepository.saveSection(section);
        }else{
            throw new IllegalArgumentException("Section already created in the stadium");
        }
    }

    public List<Stadium> getAllStadiums(){
        List<Stadium> stadiums = infrastructureRepository.getAllStadiums();
        if(stadiums != null && !stadiums.isEmpty()){
            return stadiums;
        }else{
            throw new IllegalArgumentException("No stadiums registered");
        }
    }

    public List<Map<String, Object>> getSectionsByStadiumId(int stadiumId) {
        return infrastructureRepository.findSectionsByStadiumId(stadiumId);
    }

    private void validateSection(Section section) {
        if (section.getSectionLetter() == null || !section.getSectionLetter().matches("[A-Z]")) {
            throw new IllegalArgumentException("La letra del sector debe estar entre A y Z");
        }
        if (section.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("La capacidad maxima debe ser mayor a 0");
        }
        if (section.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
}
