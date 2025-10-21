package com.adaction.backend.model;

import java.util.Map;


public class ModelCollect {
    private int id;
    private String created_at;
    private String city_id;
    private Map<Integer,Integer> wasteTypeAndQuantity;
    private int collect_id;
    private int quantity_waste;
    private int waste_id;
    private int volunteer_id;
    private String name;
    private String icone;

    public ModelCollect() {}

    public ModelCollect(int id, String created_at, String city_id, Map<Integer,Integer> wasteTypeAndQuantity, int collect_id, int waste_id, int quantity_waste, int volunteer_id) {
        this.id = id;
        this.created_at = created_at;
        this.city_id= city_id;
        this.wasteTypeAndQuantity = wasteTypeAndQuantity;
        this.collect_id = collect_id;
        this.quantity_waste = quantity_waste;
        this.waste_id = waste_id;
        this.volunteer_id = volunteer_id;
    }

    public ModelCollect(int waste_id, int quantity_waste, String name, String icone) {
        this.waste_id = waste_id;
        this.quantity_waste = quantity_waste;
        this.name = name;
        this.icone = icone;
    }

    public ModelCollect(String created_at, String city_id, String wasteTypeAndQuantity, int collect_id, int waste_id, int quantity_waste) {

    }

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCity_id() {
        return city_id;
    }

    public Map<Integer, Integer> getWasteTypeAndQuantity() {
        return wasteTypeAndQuantity;
    }

    public int getCollect_id() {
        return collect_id;
    }

    public int getWaste_id() {
        return waste_id;
    }

    public int getQuantity_waste() {
        return quantity_waste;
    }

    public int getVolunteer_id() { return volunteer_id; }

    public void setId(int id){
        this.id = id;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public void setWasteTypeAndQuantity(Map<Integer, Integer> wasteTypeAndQuantity) {
        this.wasteTypeAndQuantity = wasteTypeAndQuantity;
    }


    public void setCollect_id(int collect_id) {
        this.collect_id= collect_id;
    }

    public void setWaste_id(int waste_id) {
        this.waste_id = waste_id;
    }

    public void setQuantity_waste(int quantity_waste) {
        this.quantity_waste = quantity_waste;
    }

    public void setVolunteer_id(int volunteer_id) { this.volunteer_id = volunteer_id;};
}

