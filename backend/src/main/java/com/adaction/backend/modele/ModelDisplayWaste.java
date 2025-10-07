package com.adaction.backend.modele;

public class ModelDisplayWaste {
    private int id;
    private String type;
    private String name;
    private int point;
    private String icone;


    public ModelDisplayWaste(int id, String type, String name, int point, String icone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.point = point;
        this.icone = icone;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}