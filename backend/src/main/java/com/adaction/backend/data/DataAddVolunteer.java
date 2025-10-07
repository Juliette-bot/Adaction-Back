package com.adaction.backend.data;

import java.sql.;
import java.util.ArrayList;
import java.util.List;

public class DataAddVolunteer {
    private static final String URL = "jdbc:mysql://localhost:3306/BDDAdaction";
    private static final String USER = "root";
    private static final String PASSWORD = "Root1234!";

     try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    Statement smt = con.createStatement();
    ResultSet rs = smt.executeQuery("SELECT FROM waste")) {
}
          while (rs.next()) {  // ✅ CORRECTION ICI
        Waste waste = new Waste(
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
            }