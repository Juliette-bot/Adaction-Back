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

    private  final DatabaseProperties props;
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
    public void insertVolunteer(ModelVolunteer volunteer) {
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
                stmtCity.setInt(1, volunteer.getCity_id());
                stmtCity.setInt(2, volunteer.getCity_id());
                stmtCity.executeUpdate();
            }

            try (PreparedStatement stmtVol = conn.prepareStatement(insertVolunteerSql)) {
                stmtVol.setString(1, volunteer.getFirstName());
                stmtVol.setString(2, volunteer.getLastName());
                stmtVol.setString(3, volunteer.getEmail());
                stmtVol.setString(4, volunteer.getPass_word());
                stmtVol.setInt(5, volunteer.getCity_id());
                stmtVol.setInt(6, 0);
                stmtVol.executeUpdate();
            }

            System.out.println("✅ Ville vérifiée et bénévole ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Gather volunteer information
//    public List<Map<String, Object>> getVolunteer() {
//        List<Map<String, Object>> listVolunteer = new ArrayList<>();
//
//        String sql = "SELECT id, firstName, lastName, city_id FROM volunteer";
//
//        try (Connection conn = DriverManager.getConnection(
//                props.getUrl(), props.getUsername(), props.getPassword());
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                int cityId = rs.getInt("city_id");
//                String cityName = DataCity.getCityNameById(cityId);
//
//                Map<String, Object> volunteerMap = new HashMap();
//                volunteerMap.put("id", rs.getInt("id"));
//                volunteerMap.put("firstName", rs.getString("firstName"));
//                volunteerMap.put("lastName", rs.getString("lastName"));
//                volunteerMap.put("cityName", cityName);
//
//                listVolunteer.add(volunteerMap);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return listVolunteer;
//    }

    public List<Map<String, Object>> getVolunteerWithCity() {
        List<Map<String, Object>> listVolunteer = new ArrayList<>();

        String sql = "SELECT id, firstName, lastName, city_id FROM volunteer";

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int cityId = rs.getInt("city_id");
                String cityName = DataCity.getCityNameById(cityId); // récupère la ville via repo

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

    //Modify a volunteer information
    public List<ModelVolunteer> getInfosVolunteer() {
        List<ModelVolunteer> listInfosVolunteer = new ArrayList<>();

        String modifyVolunteerSql = """
        SELECT v.id, v.firstName, v.lastName, v.email, c.city AS cityName
        FROM volunteer v
        JOIN city c ON v.city_id = c.id
        """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery(modifyVolunteerSql);
            while (rs.next()) {
                ModelVolunteer volunteer = new ModelVolunteer(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("pass_word"),
                        rs.getString("email"),
                        rs.getInt("city_id")
                );
                listInfosVolunteer.add(volunteer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listInfosVolunteer;
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
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean updateVolunteer(int id, ModelVolunteer volunteer) {
        String sql = """
            UPDATE volunteer
            SET firstName = ?, lastName = ?, email = ?, pass_word = ?, city_id = ?, points = ?
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, volunteer.getFirstName());
            statement.setString(2, volunteer.getLastName());
            statement.setString(3, volunteer.getEmail());
            statement.setString(4, volunteer.getPass_word());
            statement.setInt(5, volunteer.getCity_id());
            statement.setInt(6, volunteer.getPoints());
            statement.setInt(7, id);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de la mise à jour du bénévole : " + e.getMessage());
            return false;
        }
    }

}
