package com.adaction.backend.controller;

import com.adaction.backend.data.DataModifyVolunteer;
import com.adaction.backend.modele.ModelChangeVolunteerVueAdmin;
import com.adaction.backend.modele.ModelDisplayVolunteer;
import com.adaction.backend.modele.ModelModifyVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/volunteer")
public class ControllerModifyVolunteer {

    private final DataModifyVolunteer volunteerInfos;

    @Autowired
    public ControllerModifyVolunteer(DataModifyVolunteer volunteerInfos) {
        this.volunteerInfos = volunteerInfos;
    }

    @GetMapping("/infos")
    public List<ModelModifyVolunteer> infosVolunteers() {
        try {
            return volunteerInfos.getInfosVolunteer();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

