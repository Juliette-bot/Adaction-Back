package com.adaction.backend.data;
import com.adaction.backend.model.ModelCity;
import com.adaction.backend.model.ModelVolunteer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataCity {

    private final DatabaseProperties props;

    public DataCity(DatabaseProperties props) {
        this.props = props;
    }

    public String getCityNameById(int city_id){
        String sql = "SELECT city FROM city WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, city_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("city");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCityIdByCityName(String city){
        String sql = "SELECT id FROM city WHERE city = ?";
        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
