package com.adaction.backend.model;

public class ModelDisplayDonation {
    String associationName;
    Integer amount;
    Integer date;

    public ModelDisplayDonation(String associationName,Integer amount,Integer date){
        this.associationName = associationName;
        this.amount = amount;
        this.date = date;
    }

}
