package com.adaction.backend.model;

public class ModelVolunteer {
    private int id;
    private String firstName;
    private String lastName;
    private String email = null;
    private String pass_word = null;
    private int city_id;
    private String cityName;
    private int points = 0;


    public ModelVolunteer(int id,String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public ModelVolunteer(int id,String firstName, String lastName, String cityName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityName =  cityName;
    }

    public ModelVolunteer(int id, String firstName, String lastName, String email, String pass_word, int city_id, String cityName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pass_word = pass_word;
        this.city_id = city_id;
        this.cityName = cityName;
    }

    public ModelVolunteer(String firstName, String lastName, String email, String pass_word, int city_id, int points){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pass_word = pass_word;
        this.city_id = city_id;
        this.points = points;
    }

    public ModelVolunteer(int id, String firstName, String lastName, String email, String pass_word, int city_id, int points){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pass_word = pass_word;
        this.city_id = city_id;
        this.points = points;
    }


    public ModelVolunteer() {}

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

    public void setFirstName(String firstName) {
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
    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}


