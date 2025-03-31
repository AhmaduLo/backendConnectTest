//package com.example.demo12.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.SignatureAlgorithm;
//import java.util.Date;
//
//@Component
//public class JwtUtils {
//    @Value("${jwt.secret}")
//    private String SECRET_KEY;
//
//    // Durée de validité du token (10 heures)
//    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
//
//    // Méthode pour générer un token JWT à partir d'un utilisateur
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)// Le sujet du token (ici l'email ou le nom d'utilisateur)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// La date d'émission du token
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)// Signature avec la clé secrète
//                .compact();
//    }
//
//    // Méthode pour extraire le nom d'utilisateur à partir du token JWT
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    // Méthode pour valider le token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
