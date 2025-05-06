package com.example.secondhand.Service;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.UtilisateurRepository;
import com.example.secondhand.Service.JwtService;
import com.example.secondhand.dto.AuthenticationRequest;
import com.example.secondhand.dto.AuthenticationResponse;
import com.example.secondhand.dto.RegisterRequest;
import com.example.secondhand.Enum.Role;
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
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setRole(Role.UTILISATEUR); // ou ADMIN selon le besoin

        utilisateurRepository.save(utilisateur);
        String jwtToken = jwtService.generateToken(utilisateur);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String jwtToken = jwtService.generateToken(utilisateur);
        return new AuthenticationResponse(jwtToken);
    }
    
}
