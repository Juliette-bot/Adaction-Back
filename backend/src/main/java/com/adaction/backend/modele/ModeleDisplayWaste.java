package com.adaction.backend.modele;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ModeleDisplayWaste {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Juliette17!";
    @GetMapping("/waste")
    public List<String> getWaste() {
        List<String> waste = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("SELECT * FROM waste")) {

            while (rs.next()) {
                waste.add(rs.getString("name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return waste;
    }

}