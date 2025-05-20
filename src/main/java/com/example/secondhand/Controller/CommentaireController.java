package com.example.secondhand.Controller;

//import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Commentaire;
//import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Service.CommentaireService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin
public class CommentaireController {

    private final CommentaireService commentaireService;
    //private final UtilisateurService utilisateurService;

    // Injecter le service via le constructeur
    
    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }
   @GetMapping
public List<Commentaire> obtenirToutesLesCommentaires() {
    return commentaireService.obtenirToutesLesCommentaires();
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
    //  @DeleteMapping("/{id}")
    // public ResponseEntity<Void> supprimer(@PathVariable Long id) {
    //     commentaireService.supprimerCommentaire(id);
    //     return ResponseEntity.noContent().build();
    // }
  @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCommentaire(@PathVariable Long id) {
        // Récupérer l'utilisateur connecté via Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Vérifier si l'utilisateur est admin
        boolean estAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
        
        commentaireService.supprimerCommentaire(id, username, estAdmin);
        return ResponseEntity.noContent().build();
    }
}
