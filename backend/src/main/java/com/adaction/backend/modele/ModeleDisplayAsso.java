package com.adaction.backend.modele;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeleDisplayAsso {

    public void maMethode(){
        List<String> malist= new ArrayList<>();
    }

    @RestController
    public static class ModelChangeVolunteerVueVolunteer {

        private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
        private static final String USER = "root";
        private static final String PASSWORD = "Juliette17!";
        @GetMapping("/city")
        public List<String> getCity() {
            List<String> city = new ArrayList<>();

            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement smt = con.createStatement();
                 ResultSet rs = smt.executeQuery("SELECT * FROM city")) {

                while (rs.next()) {
                    city.add(rs.getString("city"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return city;
        }

    }
}
