package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Commentaire;
import com.example.secondhand.Service.CommentaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin
public class CommentaireController {

    private final CommentaireService commentaireService;

    // Injecter le service via le constructeur
    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    // Créer un commentaire pour une annonce OU une réponse à un commentaire
    @PostMapping
    public ResponseEntity<Commentaire> ajouterCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire commentaireCree = commentaireService.ajouterCommentaire(commentaire);
        return ResponseEntity.ok(commentaireCree);
    }

    // Récupérer tous les commentaires d'une annonce
    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<Commentaire>> getCommentairesParAnnonce(@PathVariable Long annonceId) {
        List<Commentaire> commentaires = commentaireService.getCommentairesParAnnonce(annonceId);
        return ResponseEntity.ok(commentaires);
    }

    // Récupérer un commentaire spécifique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaire(@PathVariable Long id) {
        Commentaire commentaire = commentaireService.getCommentaire(id);
        return ResponseEntity.ok(commentaire);
    }
}
