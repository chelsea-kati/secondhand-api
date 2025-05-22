package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.secondhand.Enum.Role;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // ✅ Créer un utilisateur
    @PostMapping
    public Utilisateur creer(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.creerUtilisateur(utilisateur);
    }

    // ✅ Lister tous les utilisateurs
    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.getAllUtilisateurs();
    }

    // ✅ Obtenir un utilisateur par ID (avec ses annonces et favoris)
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }
// Endpoint pour lister tous les utilisateurs ayant le rôle UTILISATEUR Accessible uniquement par les admin
    
    @GetMapping("/role/utilisateur")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Utilisateur>> getUtilisateursSimples() {
        List<Utilisateur> utilisateurs = utilisateurService.getUtilisateursParRole(Role.UTILISATEUR);
        return ResponseEntity.ok(utilisateurs);
    }
     // Endpoint pour supprimer un utilisateur (admin uniquement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok().body(Map.of("message", "Utilisateur supprimé avec succès"));
    }
    
    // Endpoint pour désactiver un utilisateur (admin uniquement)
    @PatchMapping("/{id}/desactiver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desactiverUtilisateur(@PathVariable Long id) {
        utilisateurService.changerStatutUtilisateur(id, false);
        return ResponseEntity.ok().body(Map.of("message", "Utilisateur désactivé avec succès"));
    }
    
    // Endpoint pour réactiver un utilisateur (admin uniquement)
    @PatchMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activerUtilisateur(@PathVariable Long id) {
        utilisateurService.changerStatutUtilisateur(id, true);
        return ResponseEntity.ok().body(Map.of("message", "Utilisateur activé avec succès"));
    }
}
