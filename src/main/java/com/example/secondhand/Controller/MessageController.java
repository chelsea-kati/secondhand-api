package com.example.secondhand.Controller;

import com.example.secondhand.Entity.Message;
import com.example.secondhand.Entity.Utilisateur;
import com.example.secondhand.Service.MessageService;
import com.example.secondhand.Entity.Annonce;
import com.example.secondhand.Repository.AnnonceRepository;
import com.example.secondhand.Repository.UtilisateurRepository;
import com.example.secondhand.Repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public Message envoyerMessage(@RequestBody Message message) {
        return messageService.envoyerMessage(message);
    }

    @GetMapping("/recus/{destinataireId}")
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

    // Lier un message à une annonce (facultatif)
    @PostMapping("/envoyer")
    public ResponseEntity<Message> envoyerMessageAvecAnnonce(@RequestBody Message message,
                                                             @RequestParam(required = false) Long annonceId) {
        Message messageCree = messageService.envoyerMessage(message, annonceId);
        return ResponseEntity.ok(messageCree);
    }

    // ✅ Nouvelle route ajoutée ici
    @PostMapping("/annonce/{id}")
    public ResponseEntity<?> envoyerMessageParAnnonce(
            @PathVariable("id") Long annonceId,
            @RequestBody Message message
    ) {
        Annonce annonce = annonceRepository.findById(annonceId)
            .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée"));

        if (!annonce.isApprouvee()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("L'annonce n'est pas encore approuvée.");
        }

        // Lier l'annonce au message
        message.setAnnonce(annonce);

        // Récupérer et valider l'expéditeur
        Utilisateur expediteur = utilisateurRepository.findById(message.getExpediteur().getId())
            .orElseThrow(() -> new IllegalArgumentException("Expéditeur non trouvé"));
        message.setExpediteur(expediteur);

        // Récupérer et valider le destinataire
        Utilisateur destinataire = utilisateurRepository.findById(message.getDestinataire().getId())
            .orElseThrow(() -> new IllegalArgumentException("Destinataire non trouvé"));
        message.setDestinataire(destinataire);

        message.setDateEnvoi(new Date());

        Message messageEnregistre = messageRepository.save(message);
        return ResponseEntity.ok(messageEnregistre);
    }
}
