package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Favori;
import com.example.secondhand.Service.FavoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoris")
@CrossOrigin

public class FavoriController {
    @Autowired
    private FavoriService favoriService;

    // ✅ Ajouter un favori
    @PostMapping
    public Favori ajouterFavori(@RequestBody Favori favori) {
        return favoriService.ajouterFavori(favori);
    }

    // ✅ Lister les favoris d'un utilisateur
    @GetMapping("/utilisateur/{id}")
    public List<Favori> getFavorisParUtilisateur(@PathVariable Long id) {
        return favoriService.getFavorisParUtilisateur(id);
    }

    // ✅ Supprimer un favori
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerFavori(@PathVariable Long id) {
        favoriService.supprimerFavori(id);
        return ResponseEntity.noContent().build();
    }
    // Supprimer une annonce spécifique des favoris d’un utilisateur
@DeleteMapping("/utilisateur/{utilisateurId}/annonce/{annonceId}")
public ResponseEntity<Void> supprimerFavoriParUtilisateurEtAnnonce(@PathVariable Long utilisateurId,
                                                                   @PathVariable Long annonceId) {
    favoriService.supprimerFavoriParUtilisateurEtAnnonce(utilisateurId, annonceId);
    return ResponseEntity.noContent().build();
}
@DeleteMapping("/utilisateur/{utilisateurId}")
public ResponseEntity<?> supprimerTousLesFavoris(@PathVariable Long utilisateurId) {
    favoriService.supprimerTousLesFavorisParUtilisateur(utilisateurId);
    return ResponseEntity.ok("Tous les favoris de l'utilisateur ont été supprimés.");
}

}
