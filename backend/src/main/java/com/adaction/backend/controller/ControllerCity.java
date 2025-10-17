package com.adaction.backend.controller;

import com.adaction.backend.data.DataCity;
import com.adaction.backend.data.DataVolunteer;
import com.adaction.backend.model.ModelCity;
import com.adaction.backend.model.ModelVolunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/city")
public class ControllerCity {
    @Autowired

    private DataCity cityData;

    //Create a new volunteer
    @PostMapping("/add")
    public String addVolunteer(@RequestBody ModelVolunteer volunteer) {
        try {
            int cityId = volunteer.getCity_id();
            String cityName = cityData.getCityNameById(cityId);
            return cityName;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while adding volunteer.";
        }
    }

    @GetMapping("/cities")
    public List<ModelCity> getCities() {
        try {
            return cityData.getCities(); // ✅ Appel du repo
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // ✅ renvoie liste vide au lieu d’une String
        }
    }
}