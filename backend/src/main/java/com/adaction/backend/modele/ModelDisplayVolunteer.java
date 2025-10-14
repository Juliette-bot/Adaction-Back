package com.adaction.backend.modele;

public class ModelDisplayVolunteer {
    private int id;
    private String firstName;
    private String lastName;
    private String cityName;

    public ModelDisplayVolunteer (int id, String firstName, String lastName, String cityName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityName = cityName;
    }

    public ModelDisplayVolunteer() {}

    public int getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }


    public String getCityName(){
        return cityName;
    }
}
