package com.adaction.backend.data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "spring.datasource")

public class DatabaseProperties {
        private String url;
        private String username;
        private String password;

    public DatabaseProperties() {
        try (FileInputStream input = new FileInputStream("src/main/resources/data.properties")) {
            Properties props = new Properties();
            props.load(input);

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");

        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement du fichier de configuration : " + e.getMessage());
        }
    }


    // Getters et setters
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }


}
