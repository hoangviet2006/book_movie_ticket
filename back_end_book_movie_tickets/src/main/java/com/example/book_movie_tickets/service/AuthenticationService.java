package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.config.JwtUtil;
import com.example.book_movie_tickets.config.PasswordEncoderConfig;
import com.example.book_movie_tickets.model.User;
import com.example.book_movie_tickets.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserRepository userRepository;

    public User register(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username này đã được sử dụng!");
        }
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("Email này đã được sử dụng!");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user==null){
            throw new RuntimeException("Username không đúng! Vui lòng nhập lại");
        }
        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Mật khẩu không đúng! Vui lòng nhập lại");
        }
        return jwtUtil.generateToken(username);
    }
}
