package com.adaction.backend.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class ModelDisplayAsso {
    private int id;
    private String name;
    private String description;
    private int point;
    private String image;


    public ModelDisplayAsso(int id, String name, String description, int point, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.point = point;
        this.image = image;

    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public void steImage(String image) { this.image = image;
    }

}