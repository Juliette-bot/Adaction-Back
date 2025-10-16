package com.adaction.backend.controller;

import com.adaction.backend.data.DataCity;
import com.adaction.backend.data.DataVolunteer;
import com.adaction.backend.model.ModelVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/volunteer")
public class ControllerVolunteer {

    @Autowired
    private DataVolunteer volunteerData;

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

    //Display the list of volunteer on the admin page
    @GetMapping("/display-with-city")
    public List<Map<String, Object>> displayVolunteersWithCity() {
        return volunteerData.getVolunteerWithCity();
    }


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

    //Get the information of a volunteer with the if dor the modification form
    @PostMapping("/infos")
    public Map<String, Object> infosVolunteers(@RequestBody int id) {
        try {
            return volunteerData.getInfosVolunteer(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Problem fetching volunteer info");
        }
    }

    //Modify the information of a volunteer
    @PutMapping("/modify")
    public Map<String, String> testRequest(@RequestBody Map<String, Object> payload) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelVolunteer volunteer = new ModelVolunteer();
            volunteer.setId((int) payload.get("id"));
            volunteer.setFirstName((String) payload.get("firstName"));
            volunteer.setLastName((String) payload.get("lastName"));
            volunteer.setEmail((String) payload.get("email"));
            volunteer.setPass_word((String) payload.get("pass_word"));
            volunteer.setCity_id(0);

            // ✅ On récupère le nom de la ville depuis le JSON
            String cityName = (String) payload.get("city_id");

            volunteerData.modifyVolunteerInfo(volunteer, cityName);

            response.put("status", "success");
            response.put("message", "Volunteer modified successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error while modifying volunteer.");
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteVolunteer(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelVolunteer volunteerToDelete = new ModelVolunteer();
            volunteerToDelete.setId(id);

            String result = volunteerData.deleteVolunteer(volunteerToDelete);

            response.put("status", "success");
            response.put("message", result);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error while deleting volunteer.");
        }
        return response;
    }
}