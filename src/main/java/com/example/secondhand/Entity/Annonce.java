package com.example.secondhand.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "annonce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    private double prix;

    private LocalDate datePublication = LocalDate.now();

    private boolean approuvee = false;

    private String categorie;

    private String localisation;

    @JsonBackReference // Ajoute cette annotation pour éviter une boucle infinie
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL) // AJOUT DU CASCADE
    private List<Favori> favoris;// Relation ajoutée pour les favoris
}
