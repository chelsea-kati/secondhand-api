package com.example.secondhand.Service;

import com.example.secondhand.Entity.Favori;
import com.example.secondhand.Repository.FavoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriService {

    @Autowired
    private FavoriRepository favoriRepository;

    // Ajouter un favori (en évitant les doublons)
    public Favori ajouterFavori(Favori favori) {
        Optional<Favori> existingFavori = favoriRepository
                .findByUtilisateurIdAndAnnonceId(favori.getUtilisateur().getId(), favori.getAnnonce().getId());

        if (existingFavori.isPresent()) {
            return existingFavori.get();
        } else {
            return favoriRepository.save(favori);
        }
    }

    // Récupérer les favoris d’un utilisateur
    public List<Favori> getFavorisParUtilisateur(Long utilisateurId) {
        return favoriRepository.findByUtilisateurId(utilisateurId);
    }

    // Supprimer un favori par son ID
    public void supprimerFavori(Long id) {
        favoriRepository.deleteById(id);
    }

    // ✅ Supprimer une annonce spécifique des favoris d’un utilisateur
    public void supprimerFavoriParUtilisateurEtAnnonce(Long utilisateurId, Long annonceId) {
        Optional<Favori> favori = favoriRepository.findByUtilisateurIdAndAnnonceId(utilisateurId, annonceId);
        favori.ifPresent(favoriRepository::delete);
    }

    // ✅ Vérifier si une annonce est en favori pour un utilisateur
    public boolean estEnFavori(Long utilisateurId, Long annonceId) {
        return favoriRepository.findByUtilisateurIdAndAnnonceId(utilisateurId, annonceId).isPresent();
    }
}
