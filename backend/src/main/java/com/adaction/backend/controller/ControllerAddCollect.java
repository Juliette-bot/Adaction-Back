package com.adaction.backend.controller;

import com.adaction.backend.data.DataAddCollect;
import com.adaction.backend.model.ModelAddCollect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerAddCollect {

    private final DataAddCollect repository = new DataAddCollect();

    @PostMapping("/collect")
    public ResponseEntity<String> addCollect(@RequestBody ModelAddCollect collect) {
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

}