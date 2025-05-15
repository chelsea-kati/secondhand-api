package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Tu pourras ajouter des méthodes personnalisées ici plus tard
    //Utilisateur findByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);
    
}

