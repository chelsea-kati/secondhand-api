package com.example.secondhand.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "favori")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Ajouter l'annotation suivante qui résoudra les problèmes de références circulaires
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Favori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    // Enlever @JsonBackReference car nous utilisons maintenant @JsonIdentityInfo
    private Utilisateur utilisateur;
    
    @ManyToOne
    // Enlever @JsonBackReference car nous utilisons maintenant @JsonIdentityInfo
    private Annonce annonce;
}