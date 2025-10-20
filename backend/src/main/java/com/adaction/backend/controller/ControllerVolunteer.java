package com.adaction.backend.controller;

import com.adaction.backend.data.DataCity;
import com.adaction.backend.data.DataVolunteer;
import com.adaction.backend.data.DatabaseProperties;
import com.adaction.backend.model.ModelVolunteer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/volunteer")
public class ControllerVolunteer {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            // hash du mot de passe
            String hashedPassword = passwordEncoder.encode(volunteer.getPass_word());
            volunteer.setPass_word(hashedPassword);

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
    @GetMapping("/infos/{id}")
    public Map<String, Object> infosVolunteers(@PathVariable int id) {
        try {
            return volunteerData.getInfosVolunteer(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Problem fetching volunteer info");
        }
    }

    //Modify the information of a volunteer
    @PutMapping("/modify")
    public Map<String, String> modifyVolunteer(@RequestBody Map<String, Object> payload) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelVolunteer volunteer = new ModelVolunteer();
            volunteer.setId((int) payload.get("id"));
            volunteer.setFirstName((String) payload.get("firstName"));
            volunteer.setLastName((String) payload.get("lastName"));
            volunteer.setEmail((String) payload.get("email"));
            volunteer.setCity_id(0);

            String cityName = (String) payload.get("city_id");
            String newPassword = (String) payload.get("pass_word");

            // 🔹 Étape 1 : récupérer le mot de passe actuel
            String existingPassword = volunteerData.getExistingPassword(volunteer.getId());

            // 🔹 Étape 2 : déterminer si on doit hasher le nouveau
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(newPassword);
                volunteer.setPass_word(hashedPassword);
            } else {
                volunteer.setPass_word(existingPassword);
            }

            // 🔹 Étape 3 : mise à jour
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


    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateVolunteer(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload) {

        Map<String, String> response = new HashMap<>();

        try {
            // Création d'un objet ModelVolunteer à partir du JSON reçu
            ModelVolunteer volunteer = new ModelVolunteer();
            volunteer.setId(id); // on prend l'id depuis l'URL
            volunteer.setFirstName((String) payload.get("firstName"));
            volunteer.setLastName((String) payload.get("lastName"));
            volunteer.setCity_id(0); // valeur par défaut ou à adapter

            // Récupération du nom de la ville envoyé par le front
            String cityName = (String) payload.get("cityName");

            // Appel de ta méthode métier
            volunteerData.modifyVolunteerInfo(volunteer, cityName);

            // Réponse OK
            response.put("status", "success");
            response.put("message", "Volunteer modified successfully!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error while modifying volunteer.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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

    //Get the information of a volunteer with the id of the city
    @GetMapping("/filter/{letter}/{id}")
    public List<Map<String,Object>> filterVolunteer(
            @PathVariable String letter,
            @PathVariable String id) {

        Integer cityId = "all".equals(id) ? null : Integer.parseInt(id);
        String search = "all".equals(letter) ? null : letter;

        return volunteerData.filteringVolunteer(search, cityId);
    }


}