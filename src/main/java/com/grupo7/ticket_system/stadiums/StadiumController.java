package com.grupo7.ticket_system.stadiums;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupo7.ticket_system.models.Section;
import com.grupo7.ticket_system.models.Stadium;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/stadium")
public class StadiumController {

    @Autowired
    StadiumService stadiumService;

    @GetMapping("/getAllStadiums")
    public List<Stadium> getAllStadiums() {
        return stadiumService.getAllStadiums();
    }

    @PostMapping("/createSection")
    public Section createSection(@RequestBody Section section) {
        return stadiumService.createSection(section);
    }
}
