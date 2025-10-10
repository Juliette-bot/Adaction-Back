package com.adaction.backend.controller;
import com.adaction.backend.model.ModelDisplayWaste;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.*;
import java.util.List;
import com.adaction.backend.data.DataWaste;
import org.springframework.web.bind.annotation.*;

// LOGIQUE WEB/API
// c'est l'entrée de mon app, celle qui communique avec le front (vue ici).
// roles : recois les requetes HTTP (du navigateur ou d'une autre app), appel la couche DATA pour recuperer les modification des données puis renvois une reponse (souvant en JSON)
// Ici je communique avec mon  code du dossier DATA pour lui demander un service type Get recuperer des données, post ajouter des donneés dans la BDD etc.

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerDisplayWaste {

    private final DataWaste wasteRepository = new DataWaste();

    @GetMapping("/waste")
    public List<ModelDisplayWaste> getWaste() {
        return wasteRepository.findAll();
    }
}