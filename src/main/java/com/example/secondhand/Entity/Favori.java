package com.example.secondhand.Entity;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "favori")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Favori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   // @JsonIgnore
    @ManyToOne
    private Utilisateur utilisateur;
    //Un utilisateur peut ajouter plusieurs annonces en favori → @OneToMany
    //@JsonIgnore
    @ManyToOne
    private Annonce annonce;
    //Une annonce peut être ajoutée en favori par plusieurs utilisateurs → @ManyToOne
    
}
