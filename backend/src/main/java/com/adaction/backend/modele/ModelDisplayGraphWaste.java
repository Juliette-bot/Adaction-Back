package com.adaction.backend.modele;

public class ModelDisplayGraphWaste extends ModelAddCollect {
    Integer wasteQuantity;

    public ModelDisplayGraphWaste(Integer date, String localisation, String wasteType, Integer wasteQuantity) {
        super(date, localisation, wasteType);
        this.wasteQuantity = wasteQuantity;
    }

}
