package com.example.secondhand.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commentaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Ajouter l'annotation suivante qui résoudra les problèmes de références circulaires
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String contenu;
    
    @Column(name = "date_commentaire")
    private LocalDate dateCommentaire;
    
    @PrePersist
    public void prePersist() {
        this.dateCommentaire = LocalDate.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    // Enlever @JsonBackReference car nous utilisons maintenant @JsonIdentityInfo
    private Commentaire parent;
    
    // Enlever @JsonManagedReference car nous utilisons maintenant @JsonIdentityInfo
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Commentaire> reponses = new ArrayList<>();
}