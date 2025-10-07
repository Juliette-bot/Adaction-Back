package com.adaction.backend.controller;
import com.adaction.backend.modele.ModelDisplayWaste;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ControllerdDisplayWaste {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Juliette17!";


    @GetMapping("/waste")
    public List<ModelDisplayWaste> getWaste() {
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