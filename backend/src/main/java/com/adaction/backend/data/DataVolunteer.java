package com.adaction.backend.data;

import com.adaction.backend.model.ModelVolunteer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataVolunteer {

    private final DatabaseProperties props;
    private final DataCity DataCity;

    public DataVolunteer(DatabaseProperties props, DataCity DataCity) {
        this.props = props;
        this.DataCity = DataCity;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getUrl(),
                props.getUsername(),
                props.getPassword()
        );
    }

    //Insert a new Volunteer
    public String insertVolunteer(ModelVolunteer volunteer) {
        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {

            ;
            String insertCitySql = """
                        INSERT INTO city (city)
                        SELECT ?
                        WHERE NOT EXISTS (
                            SELECT 1 FROM city WHERE city = ?
                        )
                    """;

            try (PreparedStatement stmtCity = conn.prepareStatement(insertCitySql)) {
                stmtCity.setString(1, volunteer.getCityName());
                stmtCity.setString(2, volunteer.getCityName());
                stmtCity.executeUpdate();
            }

            String insertVolunteerSql = """
                        INSERT INTO volunteer (firstName, lastName, email, pass_word, city_id, points)
                        VALUES (?, ?, ?, ?, (SELECT id FROM city WHERE city = ?), ?)
                    """;

            try (PreparedStatement stmtVol = conn.prepareStatement(insertVolunteerSql)) {
                stmtVol.setString(1, volunteer.getFirstName());
                stmtVol.setString(2, volunteer.getLastName());
                stmtVol.setString(3, volunteer.getEmail());
                stmtVol.setString(4, volunteer.getPass_word());
                stmtVol.setString(5, volunteer.getCityName());
                stmtVol.setInt(6, 0);
                stmtVol.executeUpdate();
            }

            System.out.println("Bénévole ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getVolunteerWithCity() {
        List<Map<String, Object>> listVolunteer = new ArrayList<>();

        String sql = "SELECT id, firstName, lastName, city_id FROM volunteer";

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int cityId = rs.getInt("city_id");
                String cityName = DataCity.getCityNameById(cityId);

                Map<String, Object> volunteerMap = new HashMap<>();
                volunteerMap.put("id", rs.getInt("id"));
                volunteerMap.put("firstName", rs.getString("firstName"));
                volunteerMap.put("lastName", rs.getString("lastName"));
                volunteerMap.put("cityName", cityName);

                listVolunteer.add(volunteerMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listVolunteer;
    }

    //Get the volunteer information for the modification form with the id
    public Map<String, Object> getInfosVolunteer(int id) {
        Map<String, Object> volunteerInfosMap = new HashMap<>();

        String modifyVolunteerSql = """
                SELECT id, firstName, lastName, pass_word, email, city_id FROM volunteer WHERE id = ?
                """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement ps = conn.prepareStatement(modifyVolunteerSql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int cityId = rs.getInt("city_id");
                String cityName = DataCity.getCityNameById(cityId);

                volunteerInfosMap.put("id", rs.getInt("id"));
                volunteerInfosMap.put("firstName", rs.getString("firstName"));
                volunteerInfosMap.put("lastName", rs.getString("lastName"));
                volunteerInfosMap.put("pass_word", "");
                volunteerInfosMap.put("email", rs.getString("email"));
                volunteerInfosMap.put("city_id", cityName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return volunteerInfosMap;
    }

    //Get the firstname of a volunteer with the id
    public String getFirstname(ModelVolunteer model) {
        String sql = "SELECT firstName FROM volunteer WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, model.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userFirstname = rs.getString("firstName");
                return userFirstname;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateVolunteer(int id, ModelVolunteer volunteer) {
        String selectSql = "SELECT email, pass_word FROM volunteer WHERE id = ?";
        String updateSql = """
        UPDATE volunteer
        SET firstName = ?, lastName = ?, email = ?, pass_word = ?, city_id = ?, points = ?
        WHERE id = ?
    """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {

            // 1. Récupérer les infos actuelles
            String currentEmail = null;
            String currentPassword = null;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, id);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    currentEmail = rs.getString("email");
                    currentPassword = rs.getString("pass_word");
                }
            }

            // 2. Si les champs du front sont vides, garder ceux de la BDD
            String emailToUpdate = (volunteer.getEmail() != null && !volunteer.getEmail().isEmpty())
                    ? volunteer.getEmail() : currentEmail;
            String passwordToUpdate = (volunteer.getPass_word() != null && !volunteer.getPass_word().isEmpty())
                    ? volunteer.getPass_word() : currentPassword;

            // 3. Update
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, volunteer.getFirstName());
                updateStmt.setString(2, volunteer.getLastName());
                updateStmt.setString(3, emailToUpdate);
                updateStmt.setString(4, passwordToUpdate);
                updateStmt.setInt(5, volunteer.getCity_id());
                updateStmt.setInt(6, volunteer.getPoints());
                updateStmt.setInt(7, id);

                int rowsUpdated = updateStmt.executeUpdate();
                return rowsUpdated > 0;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de la mise à jour du bénévole : " + e.getMessage());
            return false;
        }
    }


    //Modify the information of a volunteer (admin page)
    public void modifyVolunteerInfo(ModelVolunteer volunteer,String cityName) {

        String insertCitySql = """
                INSERT INTO city (city)
                SELECT ?
                WHERE NOT EXISTS (SELECT 1 FROM city WHERE city = ?)
            """; //permet de creer la ville si elle ewxiste pas dans la table

        String updateVolunteerSql = """
                UPDATE volunteer
                SET firstName = ?, lastName = ?, email = ?, pass_word = ?, city_id = (SELECT id FROM city WHERE city = ?)
                WHERE id = ?
            """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {

            conn.setAutoCommit(false);

            // 1️⃣ Insérer la ville si elle n'existe pas
            try (PreparedStatement stmtCity = conn.prepareStatement(insertCitySql)) {
                stmtCity.setString(1, cityName);
                stmtCity.setString(2, cityName);
                stmtCity.executeUpdate();
            }

            // 2️⃣ Mettre à jour le bénévole
            try (PreparedStatement stmtVol = conn.prepareStatement(updateVolunteerSql)) {
                stmtVol.setString(1, volunteer.getFirstName());
                stmtVol.setString(2, volunteer.getLastName());
                stmtVol.setString(3, volunteer.getEmail());
                stmtVol.setString(4, volunteer.getPass_word());
                stmtVol.setString(5, cityName);
                stmtVol.setInt(6, volunteer.getId());
                stmtVol.executeUpdate();
            }

            conn.commit(); // ✅ tout s’est bien passé
            System.out.println("✅ Bénévole modifié avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            // rollback si erreur
            try (Connection conn = DriverManager.getConnection(
                    props.getUrl(), props.getUsername(), props.getPassword())) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getExistingPassword(int id) {
        try (Connection conn = DriverManager.getConnection(props.getUrl(), props.getUsername(), props.getPassword())) {
            String query = "SELECT pass_word FROM volunteer WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("pass_word");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Delete a volunteer
    public String deleteVolunteer (ModelVolunteer volunteerToDelete) {
        String sql = "DELETE FROM volunteer WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, volunteerToDelete.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Volunteer deleted successfully!";
            } else {
                return "No volunteer found with ID " + volunteerToDelete.getId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error while deleting volunteer.";
        }
    }

    public List<Map<String, Object>> filteringVolunteer(String letter, Integer id) {
        List<Map<String, Object>> listVolunteer = new ArrayList<>();
        String sql;
        if ((letter == null || letter.isEmpty()) && id != null) {
            sql = "SELECT id, firstName, lastName, city_id FROM volunteer WHERE city_id = ?";
        } else if ((letter != null && !letter.isEmpty()) && id != null) {
            sql = "SELECT id, firstName, lastName, city_id FROM volunteer WHERE (firstName LIKE ? OR lastName LIKE ?) AND city_id = ?";
        } else if ((letter != null && !letter.isEmpty()) && id == null) {
            sql = "SELECT id, firstName, lastName, city_id FROM volunteer WHERE firstName LIKE ? OR lastName LIKE ?";
        } else {
            sql = "SELECT id, firstName, lastName, city_id FROM volunteer";
        }

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (letter != null && !letter.isEmpty()) {
                String pattern = "%" + letter + "%";
                pstmt.setString(paramIndex++, pattern);
                pstmt.setString(paramIndex++, pattern);
            }
            if (id != null) {
                pstmt.setInt(paramIndex++, id);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int cityId = rs.getInt("city_id");
                    String cityName = DataCity.getCityNameById(cityId);

                    Map<String, Object> volunteerMap = new HashMap<>();
                    volunteerMap.put("id", rs.getInt("id"));
                    volunteerMap.put("firstName", rs.getString("firstName"));
                    volunteerMap.put("lastName", rs.getString("lastName"));
                    volunteerMap.put("cityName", cityName);

                    listVolunteer.add(volunteerMap);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listVolunteer;
    }

}
