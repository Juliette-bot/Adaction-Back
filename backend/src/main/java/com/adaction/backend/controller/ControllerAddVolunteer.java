package com.adaction.backend.controller;

import com.adaction.backend.modele.ModelChangeVolunteerVueAdmin;
import com.adaction.backend.data.DataAddVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/volunteer")
public class ControllerAddVolunteer {

    @Autowired
    private DataAddVolunteer volunteerData;

    @PostMapping("/add")
    public String addVolunteer(@RequestBody ModelChangeVolunteerVueAdmin volunteer) {
        try {
            volunteerData.insertVolunteer(volunteer);
            return "Volunteer added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while adding volunteer.";
        }
    }
}