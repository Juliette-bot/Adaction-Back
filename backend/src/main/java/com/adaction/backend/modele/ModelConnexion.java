package com.adaction.backend.modele;

public class ModelConnexion {
    int id;
    String email;
    String pass_word;

    public ModelConnexion() {}

    public ModelConnexion(int id, String email, String pass_word){
        this.id = id;
        this.email = email;
        this.pass_word = pass_word;
    }

    public int getId(){
        return id;
    }
    public String getEmail(){
        return email;
    }
    public String getPass_word(){
        return pass_word;
    }

    public void setEmail(String email){ this.email = email; }
    public void setPass_word(String pass_word){ this.pass_word = pass_word; }
    public void setId(int id){this.id = id;}
}
