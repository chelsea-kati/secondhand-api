package com.example.secondhand.Service;

import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Enum.Role;
import com.example.secondhand.Repository.UtilisateurRepository;
import com.example.secondhand.Security.JwtService;
import com.example.secondhand.dto.AuthenticationRequest;
import com.example.secondhand.dto.AuthenticationResponse;
import com.example.secondhand.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthentificationService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setAdresse(request.getAdresse());
        utilisateur.setRole(Role.UTILISATEUR); // maintenant on utilise l'enum

        utilisateurRepository.save(utilisateur);
        String jwtToken = jwtService.generateToken(utilisateur.getEmail());

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail());
        // if (utilisateur == null) {
        //     throw new RuntimeException("Utilisateur non trouvé");
        // }
        Utilisateur utilisateur = utilisateurRepository
    .findByEmail(request.getEmail())
    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec cet email : " + request.getEmail()));


        String jwtToken = jwtService.generateToken(utilisateur.getEmail());
        return new AuthenticationResponse(jwtToken);
    }
}
