package com.example.demo12.service;

import com.example.demo12.model.Twitte;
import com.example.demo12.repository.TwitteRepository;
import com.example.demo12.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo12.model.User;

import java.util.List;

@Service
public class TwitteService {

    @Autowired
    private TwitteRepository twitteRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer un tweet pour un utilisateur
        public Twitte createTwitte(Long userId, @Valid Twitte twitte) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            twitte.setUser(user);
            return twitteRepository.save(twitte);
        }

        //---récuperer tout les twitte----------
        public List<Twitte> getAllTwittes() {
            return twitteRepository.findAll();
        }

    // Supprimer un tweet si l'utilisateur est le propriétaire
    public void deleteTwitte(Long tweetId, Long userId) {
        Twitte tweet = twitteRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet non trouvé"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier que l'utilisateur est le propriétaire du tweet
        if (!tweet.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce tweet");
        }

        twitteRepository.delete(tweet);
    }


}
