package com.example.secondhand.dto;


public class AnnonceDTO {
    private String titre;
    private String description;

    public String getTitre(){
        return titre;
    }
    public void setTitre(String titre){
        this.titre= titre;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description= description;
    }

    
}
