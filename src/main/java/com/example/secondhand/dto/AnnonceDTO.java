package com.example.secondhand.dto;


public class AnnonceDTO {
    private Long id;
     private String titre;
    private String description;

     public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
