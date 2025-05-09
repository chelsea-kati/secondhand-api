package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    public Annonce creerAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    public List<Annonce> obtenirToutesLesAnnonces() {
        return annonceRepository.findAll();
    }

    public Optional<Annonce> obtenirAnnonceParId(Long id) {
        return annonceRepository.findById(id);
    }

    public Annonce mettreAJourAnnonce(Long id, Annonce annonceDetails) {
        Optional<Annonce> annonceExistante = annonceRepository.findById(id);
        if (annonceExistante.isPresent()) {
            Annonce annonce = annonceExistante.get();
            annonce.setTitre(annonceDetails.getTitre());
            annonce.setDescription(annonceDetails.getDescription());
            annonce.setPrix(annonceDetails.getPrix());
            annonce.setCategorie(annonceDetails.getCategorie());
            annonce.setLocalisation(annonceDetails.getLocalisation());
            // Mettez à jour d'autres champs si nécessaire
            return annonceRepository.save(annonce);
        } else {
            // Gérer le cas où l'annonce n'existe pas
            return null;
        }
    }

    // ✅ Obtenir toutes les annonces d'un utilisateur
    public List<Annonce> getAnnoncesParUtilisateur(Long utilisateurId) {
        return annonceRepository.findByUtilisateurId(utilisateurId);
    }

    // ✅ Approuver une annonce (admin)
    public boolean approuverAnnonce(Long id) {
        Optional<Annonce> annonceOpt = annonceRepository.findById(id);
        if (annonceOpt.isPresent()) {
            Annonce annonce = annonceOpt.get();
            annonce.setApprouvee(true);
            annonceRepository.save(annonce);
            return true;
        }
        return false;
    }

    public void supprimerAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }
    public List<Annonce> getAnnoncesApprouvees() {
        return annonceRepository.findByApprouveeTrue();
    }
    

}
