package com.adaction.backend.controller;

import com.adaction.backend.data.DataWaste;
import com.adaction.backend.model.ModelWaste;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerWaste {

    private final DataWaste repository;

    public ControllerWaste(DataWaste repository) {
        this.repository = repository;
    }

    @GetMapping("/waste")
    public List<ModelWaste> getWaste() {
        return repository.findAll();
    }
}
