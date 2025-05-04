package com.example.secondhand.Service;

import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Entity.Message;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
//import java.util.Optional;

@Service

public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    private AnnonceRepository annonceRepository;

    public Message envoyerMessage(Message message) {
        message.setDateEnvoi(new Date());
        return messageRepository.save(message);
    }

    public List<Message> voirMessagesRecus(Utilisateur destinataire) {
        return messageRepository.findByDestinataire(destinataire);
    }

    public List<Message> voirMessagesEnvoyes(Utilisateur expediteur) {
        return messageRepository.findByExpediteur(expediteur);
    }

    public void supprimerMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message envoyerMessage(Message message, Long annonceId) {
    if (annonceId != null) {
        Annonce annonce = annonceRepository.findById(annonceId)
            .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée"));
        message.setAnnonce(annonce);
    }

    message.setDateEnvoi(new Date());
    return messageRepository.save(message);
}
}
