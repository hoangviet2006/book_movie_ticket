package com.example.book_movie_tickets.dto;

public class JwtDto {
    private String token;

    public JwtDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
