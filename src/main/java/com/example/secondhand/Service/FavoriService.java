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

    public Favori ajouterFavori(Favori favori) {
        // return favoriRepository.save(favori);
                // Vérifier si le favori existe déjà pour cet utilisateur et cette annonce
                Optional<Favori> existingFavori = favoriRepository
                .findByUtilisateurIdAndAnnonceId(favori.getUtilisateur().getId(), favori.getAnnonce().getId());
        
        if (existingFavori.isPresent()) {
            // Si le favori existe déjà, ne pas l'ajouter à nouveau
            return existingFavori.get();
        } else {
            // Sinon, enregistrer le nouveau favori
            return favoriRepository.save(favori);
        }
    }
    

    public List<Favori> getFavorisParUtilisateur(Long utilisateurId) {
        return favoriRepository.findByUtilisateurId(utilisateurId);
    }

    public void supprimerFavori(Long id) {
        favoriRepository.deleteById(id);
    }
}
