package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.CommentaireRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AnnonceRepository annonceRepository;

    public CommentaireService(CommentaireRepository commentaireRepository,
                               UtilisateurRepository utilisateurRepository,
                               AnnonceRepository annonceRepository) {
        this.commentaireRepository = commentaireRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.annonceRepository = annonceRepository;
    }

    public Commentaire ajouterCommentaire(Commentaire commentaire) {
        // Vérification utilisateur
        Long utilisateurId = commentaire.getUtilisateur().getId();
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Vérification annonce
        Long annonceId = commentaire.getAnnonce().getId();
        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        // Si le commentaire est une réponse à un autre commentaire
        if (commentaire.getParent() != null && commentaire.getParent().getId() != null) {
            Optional<Commentaire> parent = commentaireRepository.findById(commentaire.getParent().getId());
            if (parent.isEmpty()) {
                throw new RuntimeException("Commentaire parent introuvable");
            }
            commentaire.setParent(parent.get());
        }

        commentaire.setUtilisateur(utilisateur);
        commentaire.setAnnonce(annonce);

        return commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getCommentairesParAnnonce(Long annonceId) {
        return commentaireRepository.findByAnnonceId(annonceId);
    }

    public Commentaire getCommentaire(Long id) {
        return commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire introuvable"));
    }
    
}
