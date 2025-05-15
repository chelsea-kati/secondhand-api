package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Security.CustomUserDetails;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.secondhand.Repository.UtilisateurRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces")

public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;
     @Autowired
    private UtilisateurRepository utilisateurRepository; // ✅ Ajouté

    @PostMapping
    public Annonce creerAnnonce(@RequestBody Annonce annonce) {
        return annonceService.creerAnnonce(annonce);
    }

    @GetMapping
    public List<Annonce> obtenirToutesLesAnnonces() {
        return annonceService.obtenirToutesLesAnnonces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> obtenirAnnonceParId(@PathVariable Long id) {
        Optional<Annonce> annonce = annonceService.obtenirAnnonceParId(id);
        return annonce.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Annonce> mettreAJourAnnonce(@PathVariable Long id, @RequestBody Annonce annonceDetails) {
        Annonce annonceMiseAJour = annonceService.mettreAJourAnnonce(id, annonceDetails);
        if (annonceMiseAJour != null) {
            return ResponseEntity.ok(annonceMiseAJour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//   @DeleteMapping("/{id}")
// public void supprimerAnnonce(@PathVariable Long id, @AuthenticationPrincipal Utilisateur utilisateurConnecte) {
//     annonceService.supprimerAnnonce(id, utilisateurConnecte);
// }//@AuthenticationPrincipal pour injecter l’utilisateur connecté dans la méthode du controller, 
 //puis le passe à la méthode du service que tu as déjà corrigée.
//  @DeleteMapping("/{id}")
// public void supprimerAnnonce(@PathVariable Long id,
//                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
//     Utilisateur utilisateur = utilisateurRepository.findByEmail(userDetails.getUsername())
//         .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non trouvé"));

//     annonceService.supprimerAnnonce(id, utilisateur);
// }
// @DeleteMapping("/{id}")
// public void supprimerAnnonce(@PathVariable Long id,
//     @AuthenticationPrincipal CustomUserDetails userDetails) {

//     Utilisateur utilisateur = userDetails.getUtilisateur();
//     annonceService.supprimerAnnonce(id, utilisateur);
// }
@DeleteMapping("/{id}")
public void supprimerAnnonce(@PathVariable Long id,
    @AuthenticationPrincipal CustomUserDetails userDetails) {
    
    System.out.println("DEBUG: Tentative de suppression de l'annonce " + id);
    System.out.println("DEBUG: UserDetails null? " + (userDetails == null));
    
    if (userDetails != null) {
        Utilisateur utilisateur = userDetails.getUtilisateur();
        System.out.println("DEBUG: Utilisateur email: " + utilisateur.getEmail());
        System.out.println("DEBUG: Utilisateur role: " + utilisateur.getRole());
        annonceService.supprimerAnnonce(id, utilisateur);
    } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
    }
}

    // ✅ Liste des annonces d'un utilisateur
    @GetMapping("/utilisateur/{id}")
    public List<Annonce> getAnnoncesParUtilisateur(@PathVariable Long id) {
        return annonceService.getAnnoncesParUtilisateur(id);
    }

    // ✅ Approuver une annonce (admin)
    @PutMapping("/{id}/approuver")
    public ResponseEntity<String> approuverAnnonce(@PathVariable Long id) {
        boolean approuvee = annonceService.approuverAnnonce(id);
        if (approuvee) {
            return ResponseEntity.ok("Annonce approuvée avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Liste des annonces approuvees par l'admin
    
    @GetMapping("/approuvees")
    public List<Annonce> getAnnoncesApprouvees() {
        return annonceService.getAnnoncesApprouvees();
    }

}
