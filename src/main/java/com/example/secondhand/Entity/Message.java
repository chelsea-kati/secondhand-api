package com.example.secondhand.Entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Ajouter l'annotation suivante qui résoudra les problèmes de références circulaires
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Utilisateur expediteur;
    
    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private Utilisateur destinataire;
    
    private String contenu;
    
    private Date dateEnvoi;
    
    @ManyToOne
    @JoinColumn(name = "annonce_id", nullable = true)
    private Annonce annonce;
}