package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonces")

public class AnnonceController {
    @Autowired
    private AnnonceService annonceService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAnnonce(@PathVariable Long id) {
        annonceService.supprimerAnnonce(id);
        return ResponseEntity.noContent().build();
    }
    
}
