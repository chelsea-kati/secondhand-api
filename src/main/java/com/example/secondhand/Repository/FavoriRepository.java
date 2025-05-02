package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Favori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

   public interface FavoriRepository extends JpaRepository<Favori, Long> {
        List<Favori> findByUtilisateurId(Long utilisateurId);
    
}
