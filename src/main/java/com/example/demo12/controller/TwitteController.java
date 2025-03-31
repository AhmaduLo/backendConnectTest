package com.example.demo12.controller;

import com.example.demo12.model.Twitte;
import com.example.demo12.service.TwitteService;
import com.example.demo12.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twitte")

public class TwitteController {

   @Autowired
    private TwitteService twitteService;

    // Créer un tweet pour un utilisateur
    @PostMapping("/{userId}")
    public ResponseEntity<?> createTweet(@PathVariable Long userId, @Valid @RequestBody Twitte twitte) {
        try {
            Twitte createdTweet = twitteService.createTwitte(userId, twitte);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTweet);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Gérer les exceptions de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le contenu du tweet ne peut pas être vide");
    }

    //---------GetAll twitte----------------------
    @GetMapping
    public List<Twitte> getAllTwittes() {
        return twitteService.getAllTwittes();
    }

    // Supprimer un tweet
    @DeleteMapping("/{twitteId}")
    public ResponseEntity<?> deleteTwitte(@PathVariable Long twitteId, @RequestParam Long userId) {
        try {
            twitteService.deleteTwitte(twitteId, userId);
            return ResponseEntity.ok("Twitte supprimé"); // 200 OK avec un message
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); // 403 Forbidden
        }
    }
}
