package com.example.secondhand.Entity;

import com.example.secondhand.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
//import com.tonpackage.model.Role; // adapte avec ton vrai chemin
import com.example.secondhand.Enum.Role;


import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Utilisateur implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    private String telephone;
    private String adresse;
    private LocalDate dateInscription = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Role role = Role.UTILISATEUR;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Favori> favoris;

    // ===== UserDetails methods =====

    @Override
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return java.util.List.of(
            new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + this.role.name())
        );
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
