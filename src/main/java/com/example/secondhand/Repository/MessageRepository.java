package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Message;
import com.example.secondhand.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDestinataire(Utilisateur destinataire);
    List<Message> findByExpediteur(Utilisateur expediteur);
}

    

