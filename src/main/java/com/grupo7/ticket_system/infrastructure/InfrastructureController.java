package com.grupo7.ticket_system.infrastructure;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/infrastructures")
public class InfrastructureController {

    private final InfrastructureService stadiumService;

    InfrastructureController(InfrastructureService stadiumService) {
        this.stadiumService = stadiumService;
    }

    @PostMapping("/{stadiumId}/createSection")
    public Section createSection(@RequestBody Section section, @PathVariable int stadiumId) {
        return stadiumService.createSection(section,stadiumId);
    }

    @GetMapping("/getAllStadiums")
    public List<Stadium> getAllStadiums() {
        return stadiumService.getAllStadiums();
    }
}
