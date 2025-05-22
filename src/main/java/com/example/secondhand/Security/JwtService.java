package com.example.secondhand.Security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import com.example.secondhand.Entity.Utilisateur;
//import com.example.secondhand.Enum.Role;

@Service

public class JwtService {
    // Clé secrète pour signer le token
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 864_000_00; // 1 jour en millisecondes

    // Génère un token basé sur l'email
    public String generateToken(Utilisateur utilisateur) {
        return Jwts.builder()
                //.setSubject(email)
                .setSubject(utilisateur.getEmail())
                .claim("role", utilisateur.getRole().name()) // 👉 Ajoute le rôle dans le payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Récupère l'email à partir du token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Vérifie si le token est encore valide
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
    
}
