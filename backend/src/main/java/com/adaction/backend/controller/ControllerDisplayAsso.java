package com.adaction.backend.controller;

import com.adaction.backend.data.DataDisplayAsso;
import com.adaction.backend.model.ModelDisplayAsso;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerDisplayAsso {

    private final DataDisplayAsso assoRepository = new DataDisplayAsso();

    @GetMapping("/association")
    public List<ModelDisplayAsso> getWaste() {
        return assoRepository.findAll();
    }
}