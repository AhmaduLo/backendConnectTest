package com.example.demo12.model;

import jakarta.persistence.*;

@Entity // Indique que cette classe est une entité JPA
public class Twitte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // Contenu du tweet

    // Relation Many-to-One (chaque tweet appartient à un utilisateur)
    @ManyToOne
    @JoinColumn(name = "user_id") // Colonne de clé étrangère dans la table `tweet`
    private User user;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
