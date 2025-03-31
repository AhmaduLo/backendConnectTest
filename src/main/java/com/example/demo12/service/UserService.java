package com.example.demo12.service;

import com.example.demo12.UserAlreadyExistsException;
import com.example.demo12.model.Role;
import com.example.demo12.model.User;
import com.example.demo12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Utilisez BCryptPasswordEncoder pour encoder les mots de passe
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //-------authentification user--------
    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user != null && passwordEncoder.matches(password,user.get().getPassword())) {
            return user;
        }
        return null;
    }

    // Créer un utilisateur avec un rôle
    public User createUser(User user) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec cet email existe déjà");
        }
        // Encoder le mot de passe avant de sauvegarder l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Définir un rôle par défaut si aucun rôle n'est fourni
        if (user.getRole() == null) {
            user.setRole(Role.CLIENT); // Par exemple, définir CLIENT comme rôle par défaut
        }
        return userRepository.save(user);
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // Supprimer un utilisateur par son ID
    public void deleteUserById(Long id){
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
       userRepository.deleteById(id);
    }
    //-------------admin-------------------
    // Modifier le rôle d'un utilisateur
    public User updateUserRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setRole(role);
        return userRepository.save(user);
    }


}
