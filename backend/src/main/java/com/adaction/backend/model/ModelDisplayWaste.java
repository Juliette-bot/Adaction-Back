package com.adaction.backend.model;

// REPRESENTATION DES DONNEES
// ici Le MODEL est le coeur des données de mon app. Chaque obj de ce dossier represente un entité qui corespond sourvent une table de ma BDD.
// Ici je defini ma strucuture des données et mon code sert d'ojb de transfert entre les autres couches (controleur, data...)

public class ModelDisplayWaste {
    private int id;
    private String type;
    private String name;
    private int point;
    private String icone;
    private int quantity_waste;


    public ModelDisplayWaste(int id, String type, String name, int point, String icone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.point = point;
        this.icone = icone;
        this.quantity_waste = 0;
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

    public int getQuantity_waste() { return quantity_waste; }

    public void setQuantity_waste(int quantity_waste) {
        this.quantity_waste = quantity_waste;
    }

}