package com.example.secondhand.Entity;

import jakarta.persistence.*;
import lombok.*;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @ManyToOne
    @JsonBackReference
    private Utilisateur utilisateur;
    // Un utilisateur peut ajouter plusieurs annonces en favori → @OneToMany

    @ManyToOne(cascade = CascadeType.ALL) // pour s'assurer que la suppression d'une annonce entraîne
    // la suppression de tous les favoris associés.
    private Annonce annonce;
    // Une annonce peut être ajoutée en favori par plusieurs utilisateurs →
    // @ManyToOne

}
