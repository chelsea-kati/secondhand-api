package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Favori;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.FavoriRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriService {

    @Autowired
    private FavoriRepository favoriRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    // ✅ Ajouter un favori avec entités correctement chargées
    public Favori ajouterFavori(Favori favori) {
        if (favori.getUtilisateur() == null || favori.getAnnonce() == null) {
            throw new IllegalArgumentException("Utilisateur ou annonce est manquant dans la requête");
        }

        Long utilisateurId = favori.getUtilisateur().getId();
        Long annonceId = favori.getAnnonce().getId();

        if (utilisateurId == null || annonceId == null) {
            throw new IllegalArgumentException("L'id de l'utilisateur ou de l'annonce est null");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Annonce annonce = annonceRepository.findById(annonceId)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));

        Favori nouveauFavori = new Favori();
        nouveauFavori.setUtilisateur(utilisateur);
        nouveauFavori.setAnnonce(annonce);

        return favoriRepository.save(nouveauFavori);
    }

    // ✅ Obtenir tous les favoris d'un utilisateur
    public List<Favori> getFavorisParUtilisateur(Long utilisateurId) {
        return favoriRepository.findByUtilisateurId(utilisateurId);
    }

    // ✅ Supprimer un favori par son ID
    public void supprimerFavori(Long id) {
        favoriRepository.deleteById(id);
    }
}
