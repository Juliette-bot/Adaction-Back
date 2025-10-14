package com.adaction.backend.modele;

public class ModelGetFirstname {
    String firstName;

    public ModelGetFirstname(String firstName){
        this.firstName = firstName;
    }

    public ModelGetFirstname() {}

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
