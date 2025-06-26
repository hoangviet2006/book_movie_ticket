package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.config.JwtUtil;
import com.example.book_movie_tickets.dto.CartDto;
import com.example.book_movie_tickets.repository.ICartRepository;
import com.example.book_movie_tickets.service.impl.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public List<CartDto> findAllCart(HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader!=null&& authHeader.startsWith("Bearer ")){
            String token=authHeader.substring(7);
            String username = jwtUtil.getUserNameByToken(token);
            List<CartDto> cartDtoList = cartRepository.findCartByUserName(username);
            return cartDtoList;
        }
        return null;
    }
}
