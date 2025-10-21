package com.adaction.backend.controller;

import com.adaction.backend.data.DataCollect;
import com.adaction.backend.model.ModelCollect;
import com.adaction.backend.model.ModelWaste;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerCollect {

    private final DataCollect repository;

    public ControllerCollect(DataCollect repository) {
        this.repository = repository;
    }

    @GetMapping("/city")
    public List<String> getCities() {
        return repository.getAllCities();
    }

    @PostMapping("/collect")
    public ResponseEntity<String> addCollect(@RequestBody ModelCollect collect) {
        try {
            Map<Integer, Integer> convertedMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : collect.getWasteTypeAndQuantity().entrySet()) {
                convertedMap.put(Integer.parseInt(entry.getKey().toString()), (Integer) entry.getValue());
            }
            collect.setWasteTypeAndQuantity(convertedMap);

            repository.saveCollect(collect);
            return ResponseEntity.status(HttpStatus.CREATED).body("Collecte ajoutée avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public List<Map<String, Object>> gettingTopVolunteer(){
        try {
            return repository.getBestVolunteer();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "Problem fetching volunteer info");
            return List.of(errorMap);
        }
    }

}
