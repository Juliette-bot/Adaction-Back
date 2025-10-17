package com.adaction.backend.controller;

import com.adaction.backend.data.DataAssociation;
import com.adaction.backend.model.ModelAssociation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerAssociation {

    private final DataAssociation repository;

    public ControllerAssociation(DataAssociation repository) {
        this.repository = repository;
    }


    @GetMapping("/association")
    public List<ModelAssociation> getAssociation() {
        return repository.findAll();
    }
}