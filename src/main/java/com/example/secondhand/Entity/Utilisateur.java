package com.example.secondhand.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    private String telephone;

    private String adresse;

    private LocalDate dateInscription = LocalDate.now();

    // Rôle par défaut : UTILISATEUR (tu peux créer un compte admin manuellement)
    private String role = "UTILISATEUR";

    // Relation avec les annonces créées par l'utilisateur
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    // Préparation pour les favoris (quand tu créeras l'entité Favori)
     @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
     @JsonManagedReference
     private List<Favori> favoris;
}
