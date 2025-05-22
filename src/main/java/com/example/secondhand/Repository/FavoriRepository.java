package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Favori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


   public interface FavoriRepository extends JpaRepository<Favori, Long> {
        List<Favori> findByUtilisateurId(Long utilisateurId);
          // Ajout de la méthode pour vérifier l'existence d'un favori avec utilisateur_id et annonce_id
    Optional<Favori> findByUtilisateurIdAndAnnonceId(Long utilisateurId, Long annonceId);
    void deleteByUtilisateurId(Long utilisateurId);
    
}
