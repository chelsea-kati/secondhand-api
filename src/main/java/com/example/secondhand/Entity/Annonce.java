package com.example.secondhand.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "annonce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Ajouter l'annotation suivante qui résoudra les problèmes de références circulaires
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    
    // Enlever @JsonBackReference car nous utilisons maintenant @JsonIdentityInfo
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    
    // Enlever @JsonManagedReference car nous utilisons maintenant @JsonIdentityInfo
    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    private List<Favori> favoris;
    
    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires;
}