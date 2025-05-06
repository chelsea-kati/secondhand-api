package com.example.secondhand.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commentaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;

    private LocalDate dateCommentaire = LocalDate.now();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;

    // ➕ Réponse au commentaire (relation récursive)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Commentaire parentCommentaire;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentCommentaire", cascade = CascadeType.ALL)
    private List<Commentaire> reponses;
}
