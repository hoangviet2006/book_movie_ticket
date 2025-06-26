package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.dto.CartDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ICartService {
    List<CartDto> findAllCart(HttpServletRequest httpRequest);
}
