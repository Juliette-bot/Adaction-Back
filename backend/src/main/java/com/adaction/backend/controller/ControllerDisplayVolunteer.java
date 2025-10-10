package com.adaction.backend.controller;

import com.adaction.backend.data.DataDisplayVolunteer;
import com.adaction.backend.modele.ModelDisplayVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/volunteer")
public class ControllerDisplayVolunteer {

    private final DataDisplayVolunteer volunteerData;

    @Autowired
    public ControllerDisplayVolunteer(DataDisplayVolunteer volunteerData) {
        this.volunteerData = volunteerData;
    }

    @GetMapping("/display")
    public List<ModelDisplayVolunteer> displayVolunteers() {
        try {
            return volunteerData.getVolunteer();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}



