package com.example.secondhand.Controller;
import com.example.secondhand.Entity.Message;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")

public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message envoyerMessage(@RequestBody Message message) {
        return messageService.envoyerMessage(message);
    }

    @GetMapping("/reçus/{destinataireId}")
    public List<Message> voirMessagesRecus(@PathVariable Long destinataireId) {
        Utilisateur destinataire = new Utilisateur(); 
        destinataire.setId(destinataireId);
        return messageService.voirMessagesRecus(destinataire);
    }

    @GetMapping("/envoyes/{expediteurId}")
    public List<Message> voirMessagesEnvoyes(@PathVariable Long expediteurId) {
        Utilisateur expediteur = new Utilisateur();
        expediteur.setId(expediteurId);
        return messageService.voirMessagesEnvoyes(expediteur);
    }

    @DeleteMapping("/{id}")
    public void supprimerMessage(@PathVariable Long id) {
        messageService.supprimerMessage(id);
    }
    
}
