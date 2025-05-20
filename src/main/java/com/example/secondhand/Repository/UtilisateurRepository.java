package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Enum.Role;

import java.util.List;
import java.util.Optional;
//import.java.Util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Tu pourras ajouter des méthodes personnalisées ici plus tard
    //Utilisateur findByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRole(Role role);

}

