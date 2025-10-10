package com.adaction.backend.data;

import com.adaction.backend.model.ModelDisplayWaste;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// GESTION DE LA BDD
// je parle directement a le BDD SQL
// roles : je me connect a la BDD, execute les requetes(select, insert, update, delete),
// transforme les resultats SQL en obj via mon modele (ex la via modeldisplaywaste), puis renvoir cette ojt au controleur.
// Ici je me connecte/communique avec ma BDD en faisant des requetes SQL

public class DataWaste {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Juliette17!";

    public List<ModelDisplayWaste> findAll() {
        List<ModelDisplayWaste> wastes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("SELECT * FROM waste")) {

            while (rs.next()) {
                ModelDisplayWaste waste = new ModelDisplayWaste(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("point"),
                        rs.getString("icone")
                );
                wastes.add(waste);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wastes;
    }
}