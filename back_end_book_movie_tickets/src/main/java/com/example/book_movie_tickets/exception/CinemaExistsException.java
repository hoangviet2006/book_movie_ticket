package com.example.book_movie_tickets.exception;

public class CinemaExistsException extends RuntimeException {
    public CinemaExistsException(String message) {
        super(message);
    }
}
