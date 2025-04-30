package com.example.secondhand.Repository;

import com.example.secondhand.Entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    // Déclarez ici des méthodes personnalisées si nécessaire
}
