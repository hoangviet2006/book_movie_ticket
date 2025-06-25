package com.example.book_movie_tickets.exception;

public class ShowExistsException extends RuntimeException {
    public ShowExistsException(String message) {
        super(message);
    }
}
