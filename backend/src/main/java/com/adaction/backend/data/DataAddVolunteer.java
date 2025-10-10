package com.adaction.backend.data;

import com.adaction.backend.modele.ModelChangeVolunteerVueAdmin;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DataAddVolunteer {

    private final DatabaseProperties props;

    public DataAddVolunteer(DatabaseProperties props) {
        this.props = props;
    }

    public void insertVolunteer(ModelChangeVolunteerVueAdmin volunteer) {
        String insertCitySql = """
                    INSERT INTO city (city)
                    SELECT ?
                    WHERE NOT EXISTS (
                        SELECT 1 FROM city WHERE city = ?
                    )
                """;

        String insertVolunteerSql = """
                    INSERT INTO volunteer (firstName, lastName, email, pass_word, city_id, points)
                    VALUES (?, ?, ?, ?, (SELECT id FROM city WHERE city = ?), ?)
                """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {

            try (PreparedStatement stmtCity = conn.prepareStatement(insertCitySql)) {
                stmtCity.setString(1, volunteer.getCity_id()); // ou getCityName() selon ton modèle
                stmtCity.setString(2, volunteer.getCity_id());
                stmtCity.executeUpdate();
            }

            try (PreparedStatement stmtVol = conn.prepareStatement(insertVolunteerSql)) {
                stmtVol.setString(1, volunteer.getFirstName());
                stmtVol.setString(2, volunteer.getLastName());
                stmtVol.setString(3, volunteer.getEmail());
                stmtVol.setString(4, volunteer.getPass_word());
                stmtVol.setString(5, volunteer.getCity_id());
                stmtVol.setInt(6, 0);
                stmtVol.executeUpdate();
            }

            System.out.println("✅ Ville vérifiée et bénévole ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
