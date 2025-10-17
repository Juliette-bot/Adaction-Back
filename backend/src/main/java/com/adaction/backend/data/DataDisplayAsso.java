package com.adaction.backend.data;

import com.adaction.backend.model.ModelDisplayAsso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataDisplayAsso {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Juliette17!";

    public List<ModelDisplayAsso> findAll() {
        List<ModelDisplayAsso> assos = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("SELECT * FROM association")) {

            while (rs.next()) {
                ModelDisplayAsso asso = new ModelDisplayAsso(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("point"),
                        rs.getString("image")
                );
                assos.add(asso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assos;
    }
}