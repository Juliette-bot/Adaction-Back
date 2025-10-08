package com.adaction.backend.modele;

public class ModelChangeVolunteerVueAdmin {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String pass_word;
    private String city_id;
    private int points;

    public ModelChangeVolunteerVueAdmin(int id, String firstName, String lastName, String email, String pass_word, String city_id, int points){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pass_word = pass_word;
        this.city_id = city_id;
        this.points = points;
    }

    public ModelChangeVolunteerVueAdmin() {}

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }
    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}


