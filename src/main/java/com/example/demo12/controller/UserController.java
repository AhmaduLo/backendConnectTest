package com.example.demo12.controller;

import com.example.demo12.UserAlreadyExistsException;
import com.example.demo12.model.Role;
import com.example.demo12.model.User;
//import com.example.demo12.security.JwtUtils;
import com.example.demo12.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private JwtUtils jwtUtils;

    //-----post pour le login-------
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // 1. Authentification de l'utilisateur
        Optional<User> authenticatedUser = userService.authenticateUser(
                user.getEmail(),
                user.getPassword()
        );

        if (!authenticatedUser.isPresent()) {
            // 2. Si authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect");
        }
        // 3. Génération du JWT si authentification réussie
         user = authenticatedUser.get();
        //String token = jwtUtils.generateToken(user.getEmail());

        // 4. Construction de la réponse
        Map<String, Object> response = new HashMap<>();
        //response.put("token", token);
        response.put("user", user); // Optionnel: renvoyer les infos utilisateur

        return ResponseEntity.ok(response);
    }

    // Ajouter un utilisateur
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Gérer les exceptions de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tous les champs sont obligatoires");
    }

    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Récupérer tout les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //----------delete un user--------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // Modifier le rôle d'un utilisateur (accessible uniquement par un ADMIN)
    @PutMapping("/admin/{userId}/role")
    public User updateUserRole(@PathVariable Long userId, @RequestParam Role role) {
        return userService.updateUserRole(userId, role);
    }
}

