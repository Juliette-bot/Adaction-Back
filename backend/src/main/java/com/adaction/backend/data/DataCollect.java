package com.adaction.backend.data;

import com.adaction.backend.model.ModelCollect;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataCollect {

    private final DatabaseProperties props;

    public DataCollect(DatabaseProperties props) {
        this.props = props;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getUrl(),
                props.getUsername(),
                props.getPassword()
        );
    }

    // 🔹 1️⃣ — Récupération des villes
    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT city FROM city";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cities.add(rs.getString("city"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des villes : " + e.getMessage());
            e.printStackTrace();
        }

        return cities;
    }

    // 🔹 2️⃣ — Enregistrement d'une collecte
    public void saveCollect(ModelCollect collect) {
        String sqlFindCityId = "SELECT id FROM city WHERE city = ?";
        String sqlCollect = "INSERT INTO collect (created_at, city_id, volunteer_id) VALUES (?, ?, ?)";
        String sqlWasteCollect = "INSERT INTO waste_collect (collect_id, waste_id, quantity_waste) VALUES (?, ?, ?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            // 🔸 Récupération de l'ID de la ville
            int cityId;
            try (PreparedStatement stmtCity = con.prepareStatement(sqlFindCityId)) {
                stmtCity.setString(1, collect.getCity_id());
                try (ResultSet rs = stmtCity.executeQuery()) {
                    if (rs.next()) {
                        cityId = rs.getInt("id");
                    } else {
                        throw new SQLException("Ville non trouvée : " + collect.getCity_id());
                    }
                }
            }

            // 🔸 Insertion de la collecte
            int collectId;
            try (PreparedStatement stmt = con.prepareStatement(sqlCollect, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, collect.getCreated_at());
                stmt.setInt(2, cityId);
                stmt.setInt(3, collect.getVolunteer_id()); // ✅ On ajoute ici l'id du volunteer
                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        collectId = keys.getInt(1);
                    } else {
                        throw new SQLException("Échec de la récupération de l'ID de la collecte");
                    }
                }
            }

            // 🔸 Insertion des déchets associés
            try (PreparedStatement stmtWaste = con.prepareStatement(sqlWasteCollect)) {
                for (Map.Entry<Integer, Integer> entry : collect.getWasteTypeAndQuantity().entrySet()) {
                    stmtWaste.setInt(1, collectId);
                    stmtWaste.setInt(2, entry.getKey());
                    stmtWaste.setInt(3, entry.getValue());
                    stmtWaste.addBatch();
                }
                stmtWaste.executeBatch();
            }

            con.commit();
            System.out.println(" Collecte enregistrée avec succès (ID: " + collectId + ", Volunteer ID: " + collect.getVolunteer_id() + ")");

        } catch (SQLException e) {
            System.err.println(" Erreur lors de l'enregistrement de la collecte : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getBestVolunteer(){
        List<Map<String, Object>> listVolunteer = new ArrayList<>();
        String sql = "SELECT volunteer.firstName, " +
                "volunteer.lastName, " +
                "COUNT(collect.id) AS collect_number " +
                "FROM volunteer " +
                "JOIN collect ON volunteer.id = collect.volunteer_id " +
                "GROUP BY volunteer.id, volunteer.firstName, volunteer.lastName " +
                "ORDER BY collect_number DESC " +
                "LIMIT 5;";

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> volunteerMap = new HashMap<>();
                volunteerMap.put("firstName", rs.getString("firstName"));
                volunteerMap.put("lastName", rs.getString("lastName"));
                volunteerMap.put("collect_number", rs.getInt("collect_number"));
                listVolunteer.add(volunteerMap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listVolunteer;
    }
}

