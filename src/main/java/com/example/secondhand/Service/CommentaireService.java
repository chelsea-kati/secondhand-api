package com.example.secondhand.Service;

import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Repository.CommentaireRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
import com.example.secondhand.Repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    public Commentaire ajouterCommentaire(Commentaire commentaire, Long utilisateurId, Long annonceId, Long parentId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée"));

        commentaire.setUtilisateur(utilisateur);
        commentaire.setAnnonce(annonce);
        commentaire.setDateCommentaire(LocalDate.now());

        if (parentId != null) {
            Commentaire parent = commentaireRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Commentaire parent non trouvé"));
            commentaire.setParent(parent);
        }

        return commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getCommentairesByAnnonce(Long annonceId) {
        return commentaireRepository.findByAnnonceId(annonceId);
    }
}
