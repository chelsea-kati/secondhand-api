package com.example.secondhand.Service;

import com.example.secondhand.Entity.Favori;
import com.example.secondhand.Repository.FavoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriService {

    @Autowired
    private FavoriRepository favoriRepository;

    public Favori ajouterFavori(Favori favori) {
        return favoriRepository.save(favori);
    }

    public List<Favori> getFavorisParUtilisateur(Long utilisateurId) {
        return favoriRepository.findByUtilisateurId(utilisateurId);
    }

    public void supprimerFavori(Long id) {
        favoriRepository.deleteById(id);
    }
}
