package com.example.secondhand.Service;

import com.example.secondhand.dto.AuthenticationRequest;
import com.example.secondhand.dto.AuthenticationResponse;
import com.example.secondhand.dto.RegisterRequest;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Enum.Role;
import com.example.secondhand.Repository.UtilisateurRepository;
import com.example.secondhand.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // 🔐 Enregistrement d’un nouvel utilisateur
    public AuthenticationResponse register(RegisterRequest request) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setAdresse(request.getAdresse());

        // ⚠️ Si aucun rôle n’est précisé dans le register, on attribue UTILISATEUR par défaut
        utilisateur.setRole(request.getRole() != null ? request.getRole() : Role.UTILISATEUR);

        utilisateurRepository.save(utilisateur);

        // ✅ On génère un token avec les infos complètes (email + rôle)
        String token = jwtService.generateToken(utilisateur);

        return new AuthenticationResponse(token);
    }

    // 🔐 Authentification d’un utilisateur existant
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Vérifie que l’email et le mot de passe sont valides
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Récupère l’utilisateur en base
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // ✅ On génère un token avec les infos de l’utilisateur (email + rôle)
        String token = jwtService.generateToken(utilisateur);

        return new AuthenticationResponse(token);
    }
}
