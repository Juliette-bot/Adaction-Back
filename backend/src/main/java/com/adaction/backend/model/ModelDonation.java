package com.adaction.backend.model;

public class ModelDonation {
    String associationName;
    Integer amount;
    Integer date;

    public ModelDonation(String associationName, Integer amount, Integer date){
        this.associationName = associationName;
        this.amount = amount;
        this.date = date;
    }

}
