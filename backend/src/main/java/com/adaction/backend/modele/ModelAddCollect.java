package com.adaction.backend.modele;

public class ModelAddCollect {
    Integer date;
    String localisation;
    String wasteType;

    public ModelAddCollect (Integer date, String localisation, String wasteType) {
        this.date = date;
        this.localisation = localisation;
        this.wasteType = wasteType;
    }

}
