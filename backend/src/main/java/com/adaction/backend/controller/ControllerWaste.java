package com.adaction.backend.controller;

import com.adaction.backend.data.DataWaste;
import com.adaction.backend.model.ModelCollect;
import com.adaction.backend.model.ModelWaste;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControllerWaste {

    private final DataWaste repository;

    public ControllerWaste(DataWaste repository) {
        this.repository = repository;
    }

    @GetMapping("/waste")
    public List<ModelWaste> getWaste() {
        return repository.findAll();
    }


    @GetMapping("/waste/{id}/{month}/{year}")
    public List<ModelWaste> getWaste(@PathVariable int id, @PathVariable int month, @PathVariable int year) {
        try {
            return repository.getWasteCollectMonth(id, month, year);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
