package com.adaction.backend.controller;

import com.adaction.backend.data.DataVolunteer;
import com.adaction.backend.data.DatabaseProperties;
import com.adaction.backend.model.ModelVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/volunteer")
public class ControllerVolunteer {

    @Autowired
    private DataVolunteer volunteerData;
    private DatabaseProperties props;
    private final DataVolunteer dataVolunteer;

    public ControllerVolunteer(DataVolunteer dataVolunteer) {
        this.dataVolunteer = dataVolunteer;
    }

    //Create a new volunteer
    @PostMapping("/add")
    public String addVolunteer(@RequestBody ModelVolunteer volunteer) {
        try {
            volunteerData.insertVolunteer(volunteer);
            return "Volunteer added successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while adding volunteer.";
        }
    }

    @GetMapping("/display-with-city")
    public List<Map<String, Object>> displayVolunteersWithCity() {
        return volunteerData.getVolunteerWithCity(); // méthode qui construit Map avec cityName
    }

    //Display volunteer information on the admin page
//    @GetMapping("/display")
//    public List<ModelVolunteer> displayVolunteers() {
//        try {
//            return volunteerData.getVolunteer();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return List.of();
//        }
//    }

    // Display the firstname of the volunteer with the id
    @PostMapping("/dashboard/userId")
    public String getUserFirstname(@RequestBody ModelVolunteer firstname) {
        try {
            String userFirstname = volunteerData.getFirstname(firstname);
            return userFirstname;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while get the firstname.";
        }
    }

    //Modify the information of a volunteer
    @GetMapping("/modify")
    public List<ModelVolunteer> infosVolunteers() {
        try {
            return volunteerData.getInfosVolunteer();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModelVolunteer> updateVolunteer(
            @PathVariable int id,
            @RequestBody ModelVolunteer volunteer) {

        boolean updated = dataVolunteer.updateVolunteer(id, volunteer);

        if (updated) {
            System.out.println("Bénévole mis à jour avec succès : " + volunteer.getFirstName());
            return ResponseEntity.ok(volunteer);
        } else {
            System.err.println("Aucun bénévole trouvé avec l'id : " + id);
            return ResponseEntity.notFound().build();
        }
    }

}