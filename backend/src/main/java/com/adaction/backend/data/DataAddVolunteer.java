package com.adaction.backend.data;

import com.adaction.backend.model.ModelChangeVolunteerVueAdmin;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DataAddVolunteer {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Root1234!";

    public void insertVolunteer(ModelChangeVolunteerVueAdmin volunteer) throws SQLException {
        String sql = "INSERT INTO volunteer (firstName, lastName, email, pass_word, city_id, points) VALUES (?, ?, ?, ?, (SELECT id FROM city WHERE city = ?), ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, volunteer.getFirstName());
            stmt.setString(2, volunteer.getLastName());
            stmt.setString(3, volunteer.getEmail());
            stmt.setString(4, volunteer.getPass_word());
            stmt.setString(5, volunteer.getCity_id());
            stmt.setInt(6, 0);

            stmt.executeUpdate();
        }
    }
}