package com.adaction.backend.data;
import com.adaction.backend.modele.ModelDisplayVolunteer;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDisplayVolunteer {

    private final DatabaseProperties props;

    public DataDisplayVolunteer(DatabaseProperties props) {
        this.props = props;
    }

    public List<ModelDisplayVolunteer> getVolunteer() {
        List<ModelDisplayVolunteer> listVolunteer = new ArrayList<>();

        String modifyVolunteerSql = """
        SELECT v.id, v.firstName, v.lastName, c.city AS cityName
        FROM volunteer v
        JOIN city c ON v.city_id = c.id
        """;

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword())) {
                Statement smt = conn.createStatement();
                ResultSet rs = smt.executeQuery(modifyVolunteerSql);
                while (rs.next()) {
                    ModelDisplayVolunteer volunteer = new ModelDisplayVolunteer(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("cityName")

                    );
                    listVolunteer.add(volunteer);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return listVolunteer;
        }
    }
