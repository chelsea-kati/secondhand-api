package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    // Créer un commentaire pour une annonce, ou une réponse à un commentaire
    @PostMapping("/annonce/{annonceId}")
    public ResponseEntity<Commentaire> commenterAnnonce(
            @RequestBody Commentaire commentaire,
            @PathVariable Long annonceId,
            @RequestParam Long utilisateurId,
            @RequestParam(required = false) Long parentId) {

        Commentaire commentaireCree = commentaireService.ajouterCommentaire(commentaire, utilisateurId, annonceId, parentId);
        return ResponseEntity.ok(commentaireCree);
    }

    // Afficher tous les commentaires d'une annonce
    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<Commentaire>> getCommentairesParAnnonce(@PathVariable Long annonceId) {
        List<Commentaire> commentaires = commentaireService.getCommentairesParAnnonce(annonceId);
        return ResponseEntity.ok(commentaires);
    }

    // Supprimer un commentaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCommentaire(@PathVariable Long id) {
        commentaireService.supprimerCommentaire(id);
        return ResponseEntity.noContent().build();
    }
}
