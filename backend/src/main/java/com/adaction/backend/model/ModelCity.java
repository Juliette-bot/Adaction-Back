package com.adaction.backend.model;

public class ModelCity {
    int id;
    String city;
    float latitude;
    float longitude;

    public ModelCity(int id, String city, float latitude, float longitude){
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude= longitude;
    }

    public ModelCity(int id, String city){
        this.id = id;
        this.city = city;
    }

    public ModelCity(){}

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
