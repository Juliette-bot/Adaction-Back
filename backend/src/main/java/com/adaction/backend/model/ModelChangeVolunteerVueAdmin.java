package com.adaction.backend.model;

public class ModelChangeVolunteerVueAdmin {
    String firstname;
    String lastname;
    String email;
    String password;
    String localisation;

    public ModelChangeVolunteerVueAdmin(String firstname, String lastname, String email, String password, String localisation){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.localisation = localisation;
    }
}
