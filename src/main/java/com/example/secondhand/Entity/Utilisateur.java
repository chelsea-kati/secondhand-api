package com.example.secondhand.Entity;

import com.example.secondhand.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
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

    @Enumerated(EnumType.STRING)
    private Role role = Role.UTILISATEUR;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Favori> favoris;
}