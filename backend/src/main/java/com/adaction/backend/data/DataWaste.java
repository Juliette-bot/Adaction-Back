package com.adaction.backend.data;

import com.adaction.backend.model.ModelCollect;
import com.adaction.backend.model.ModelWaste;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// GESTION DE LA BDD
// je parle directement a le BDD SQL
// roles : je me connect a la BDD, execute les requetes(select, insert, update, delete),
// transforme les resultats SQL en obj via mon modele (ex la via modeldisplaywaste), puis renvoir cette ojt au controleur.
// Ici je me connecte/communique avec ma BDD en faisant des requetes SQL
@Repository
public class DataWaste {


    private final DatabaseProperties props;

    public DataWaste(DatabaseProperties props) {
        this.props = props;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getUrl(),
                props.getUsername(),
                props.getPassword()
        );
    }

    public List<ModelWaste> findAll() {
        List<ModelWaste> wastes = new ArrayList<>();
        String sql = "SELECT * FROM waste";


        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelWaste waste = new ModelWaste(
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

    public List<ModelWaste> getWasteCollectMonth(int volunteerId, int month, int year) {
        List<ModelWaste> listWaste = new ArrayList<>();

        String sql = "SELECT waste_collect.waste_id, waste.name, waste.icone, SUM(waste_collect.quantity_waste) AS total_quantity " +
                "FROM waste_collect " +
                "JOIN collect ON waste_collect.collect_id = collect.id " +
                "JOIN waste ON waste_collect.waste_id = waste.id " +
                "WHERE collect.volunteer_id = ? " +
                "AND YEAR(collect.created_at) = ? " +
                "AND MONTH(collect.created_at) = ? " +
                "GROUP BY waste_collect.waste_id, waste.name, waste.icone";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, volunteerId);
            stmt.setInt(2, year);
            stmt.setInt(3, month);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int wasteId = rs.getInt("waste_id");
                    String name = rs.getString("name");
                    String icone = rs.getString("icone");
                    int totalQuantity = rs.getInt("total_quantity");

                    listWaste.add(new ModelWaste(wasteId, name, icone, totalQuantity));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listWaste;
    }
}