package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.CommentaireRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    public Commentaire ajouterCommentaire(Commentaire commentaire, Long utilisateurId, Long annonceId, Long parentId) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
        Optional<Annonce> annonceOpt = annonceRepository.findById(annonceId);

        if (utilisateurOpt.isPresent() && annonceOpt.isPresent()) {
            commentaire.setUtilisateur(utilisateurOpt.get());
            commentaire.setAnnonce(annonceOpt.get());

            if (parentId != null) {
                Optional<Commentaire> parentCommentaire = commentaireRepository.findById(parentId);
                parentCommentaire.ifPresent(commentaire::setParent);
            }

            return commentaireRepository.save(commentaire);
        }

        throw new RuntimeException("Utilisateur ou Annonce non trouvé.");
    }

    public List<Commentaire> getCommentairesParAnnonce(Long annonceId) {
        return commentaireRepository.findByAnnonceId(annonceId);
    }

    public void supprimerCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }
}
