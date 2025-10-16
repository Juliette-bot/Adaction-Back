package com.adaction.backend.data;

import com.adaction.backend.model.ModelAssociation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataAssociation {

    private final DatabaseProperties props;

    public DataAssociation(DatabaseProperties props) {
        this.props = props;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getUrl(),
                props.getUsername(),
                props.getPassword()
        );
    }

    public List<ModelAssociation> findAll() {
        List<ModelAssociation> assos = new ArrayList<>();
        String sql = "SELECT * FROM association";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelAssociation asso = new ModelAssociation(
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