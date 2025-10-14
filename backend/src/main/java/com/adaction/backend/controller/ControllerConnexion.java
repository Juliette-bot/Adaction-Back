package com.adaction.backend.controller;

import com.adaction.backend.data.DataConnexion;
import com.adaction.backend.modele.ModelConnexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/adaction")
public class ControllerConnexion {

    @Autowired
    private DataConnexion dataConnexion;

    @PostMapping("/connexion")
    public ResponseEntity<String> login(@RequestBody ModelConnexion login) {

        int id = dataConnexion.verifyLogin(login);
        if (id != -1) {
            System.out.println("Connexion réussie !");
            return ResponseEntity.ok("{\"message\": \"Connexion réussie\", \"id\": " + id + "}");
        } else {
            return ResponseEntity.status(401).body("{Email ou mot de passe incorrect}");
        }
    }
}
