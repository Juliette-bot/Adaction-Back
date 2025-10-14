package com.adaction.backend.data;

import com.adaction.backend.model.ModelAddCollect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataAddCollect {

    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Root1234!";


    // 🔹 1️⃣ — Récupération des villes (pour ton /city)
    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT city FROM city";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
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


    public void saveCollect(ModelAddCollect collect) {
        String sqlFindCityId = "SELECT id FROM city WHERE city = ?";
        String sqlCollect = "INSERT INTO collect (created_at, city_id) VALUES (?, ?)";
        String sqlWasteCollect = "INSERT INTO waste_collect (collect_id, waste_id, quantity_waste) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            con.setAutoCommit(false); // début de transaction

            // 🔍 Étape 1 : Trouver l'ID de la ville à partir de son nom
            int cityId;
            try (PreparedStatement stmtCity = con.prepareStatement(sqlFindCityId)) {
                stmtCity.setString(1, collect.getCity_id()); // on cherche la ville par son nom
                try (ResultSet rs = stmtCity.executeQuery()) {
                    if (rs.next()) {
                        cityId = rs.getInt("id");
                        System.out.println("🏙️ Ville trouvée : " + collect.getCity_id() + " (ID: " + cityId + ")");
                    } else {
                        throw new SQLException("Ville non trouvée : " + collect.getCity_id());
                    }
                }
            }

            // 📝 Étape 2 : Insertion dans la table collect
            int collectId;
            try (PreparedStatement stmt = con.prepareStatement(sqlCollect, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, collect.getCreated_at());
                stmt.setInt(2, cityId);
                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        collectId = keys.getInt(1);
                    } else {
                        throw new SQLException("Échec de la récupération de l'ID de la collecte");
                    }
                }
            }

            // ♻️ Étape 3 : Insertion dans waste_collect pour chaque type de déchet
            try (PreparedStatement stmtWaste = con.prepareStatement(sqlWasteCollect)) {
                for (Map.Entry<Integer, Integer> entry : collect.getWasteTypeAndQuantity().entrySet()) {
                    int wasteId = entry.getKey();
                    int quantity = entry.getValue();

                    stmtWaste.setInt(1, collectId);
                    stmtWaste.setInt(2, wasteId);
                    stmtWaste.setInt(3, quantity);
                    stmtWaste.addBatch();
                }

                stmtWaste.executeBatch();
            }

            // ✅ Étape 4 : Commit
            con.commit();
            System.out.println("✅ Collecte enregistrée avec succès (ID: " + collectId + ")");

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'enregistrement de la collecte : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
