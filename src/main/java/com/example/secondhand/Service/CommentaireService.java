package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.CommentaireRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
//import com.example.secondhand.Enum.Role;
import org.springframework.http.HttpStatus;
//import com.example.secondhand.Exception.ResourceNotFoundException;
//import com.example.secondhand.Exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    // public void supprimerCommentaire(Long id) {
    //     commentaireRepository.deleteById(id);
    // }
    
    
     public List<Commentaire> obtenirToutesLesCommentaires() {
        return commentaireRepository.findAll();
    }
        // Méthode simplifiée : supprime directement le commentaire sans vérification
    public void supprimerCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }
    
    // Nouvelle méthode : vérifie si l'utilisateur peut supprimer le commentaire
   
    public void supprimerCommentaire(Long id, String username, boolean estAdmin) {
        Optional<Commentaire> commentaireOpt = commentaireRepository.findById(id);
        
        if (commentaireOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commentaire non trouvé");
        }
        
        Commentaire commentaire = commentaireOpt.get();
        
        // Vérifier si l'utilisateur est le propriétaire ou un admin
        boolean estProprietaire = commentaire.getUtilisateur().getUsername().equals(username);
        
        if (!estProprietaire && !estAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Non autorisé");
        }
        
        commentaireRepository.deleteById(id);
    }

}
