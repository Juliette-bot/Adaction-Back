package com.adaction.backend.data;

import com.adaction.backend.model.ModelConnexion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DataConnexion {

    private final PasswordEncoder passwordEncoder;
    private final DatabaseProperties props;

    public DataConnexion(DatabaseProperties props, PasswordEncoder passwordEncoder) {
        this.props = props;
        this.passwordEncoder = passwordEncoder;
    }

    public Integer verifyLogin(ModelConnexion login) {
        String sql = "SELECT id, email, pass_word FROM volunteer WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(
                props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login.getEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("pass_word");
                int id = rs.getInt("id");

                if (passwordEncoder.matches(login.getPass_word(), dbPassword)) {
                    return id;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
