package com.adaction.backend.configuration;

import com.adaction.backend.data.DatabaseProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class HashPassword implements CommandLineRunner {

    private final DatabaseProperties props; // ta classe avec url/username/password
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public HashPassword(DatabaseProperties props) {
        this.props = props;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start password migration (read-only check mode by default).");

        String selectSql = "SELECT id, email, pass_word FROM volunteer";
        String updateSql = "UPDATE volunteer SET pass_word = ? WHERE id = ?";

        // Mode: si true, exécution réelle; si false, fait juste un "dry-run" (recommandé au début)
        boolean executeUpdates = true; // <- passe à true quand tu es sûr

        try (Connection conn = DriverManager.getConnection(props.getUrl(), props.getUsername(), props.getPassword());
             PreparedStatement psSelect = conn.prepareStatement(selectSql);
             ResultSet rs = psSelect.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String email = rs.getString("email");
                String dbPass = rs.getString("pass_word");

                // Détecte si déjà un hash BCrypt (commence par $2a$ / $2b$ / $2y$)
                boolean alreadyHashed = dbPass != null && dbPass.startsWith("$2");

                if (alreadyHashed) {
                    System.out.println("SKIP id=" + id + " email=" + email + " (already hashed)");
                    continue;
                }

                // dbPass est en clair => générer hash
                String hashed = encoder.encode(dbPass);

                System.out.println("Will update id=" + id + " email=" + email + " -> hashed length " + hashed.length());

                if (executeUpdates) {
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setString(1, hashed);
                        psUpdate.setLong(2, id);
                        int updated = psUpdate.executeUpdate();
                        System.out.println("Updated id=" + id + " rows=" + updated);
                    } catch (Exception ex) {
                        System.err.println("Failed updating id=" + id + " : " + ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Password migration finished. executeUpdates = " + executeUpdates);
    }
}
