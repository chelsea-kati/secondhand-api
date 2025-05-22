package com.example.secondhand.Service;

import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.UtilisateurRepository;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.secondhand.Enum.Role;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id);
    }
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
    }

    // public void supprimerUtilisateur(Long id) {
    //     utilisateurRepository.deleteById(id);
    // }
    // Récupère tous les utilisateurs ayant le rôle UTILISATEUR
      public List<Utilisateur> getUtilisateursParRole(Role role) {
        return utilisateurRepository.findByRole(role);
    }
public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = getUtilisateurById(id);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        // Vérifier que l'utilisateur n'est pas l'admin qui effectue la suppression
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName().equals(utilisateur.getEmail())) {
            throw new RuntimeException("Impossible de supprimer votre propre compte");
        }
        utilisateurRepository.delete(utilisateur);
    }

    public Utilisateur changerStatutUtilisateur(Long id, boolean statut) {
        Utilisateur utilisateur = getUtilisateurById(id);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        // Vérifier que l'utilisateur n'est pas l'admin qui effectue la désactivation
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName().equals(utilisateur.getEmail())) {
            throw new RuntimeException("Impossible de modifier le statut de votre propre compte");
        }
        utilisateur.setActive(statut);
        return utilisateurRepository.save(utilisateur);
    }
}
