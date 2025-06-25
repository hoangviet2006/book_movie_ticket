package com.example.book_movie_tickets.exception;

public class MovieExistsException extends RuntimeException {
    public MovieExistsException(String message) {
        super(message);
    }
}
