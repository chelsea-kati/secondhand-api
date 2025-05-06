package com.example.secondhand.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Commentaire parent;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Commentaire> reponses = new ArrayList<>();
}
