package com.adaction.backend.data;


import com.adaction.backend.modele.ModelModifyVolunteer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataModifyVolunteer {
    private final DatabaseProperties props;

    public DataModifyVolunteer(DatabaseProperties props) {
        this.props = props;
    }

    public List<ModelModifyVolunteer> getInfosVolunteer() {
        List<ModelModifyVolunteer> listInfosVolunteer = new ArrayList<>();

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
                ModelModifyVolunteer volunteer = new ModelModifyVolunteer(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("pass_word"),
                        rs.getString("email"),
                        rs.getString("cityName")
                );
                listInfosVolunteer.add(volunteer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listInfosVolunteer;
    }
}
