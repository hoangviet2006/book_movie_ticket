package com.example.book_movie_tickets.config;

import com.example.book_movie_tickets.model.User;
import com.example.book_movie_tickets.repository.IUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtUtil {
    @Autowired
    private IUserRepository userRepository;
    String secret = "my_super_secret_key_that_is_long_enough_32_bytes"; // khóa của jwt
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new RuntimeException("User không tồn tại trong hệ thống!");
        }
        return Jwts.builder() // tạo token
                .setSubject(username) // thông tin chính
                .claim("email", user.getEmail())  // thông tin phụ
                .claim("role","ROLE_"+user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))// tồn tại trong 24h
                .signWith(key) // kys token
                .compact();
    }

    public String getUserNameByToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleByToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String getEmailByToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("email", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);// xác minh token
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
