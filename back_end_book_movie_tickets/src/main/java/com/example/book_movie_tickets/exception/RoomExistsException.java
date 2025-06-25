package com.example.book_movie_tickets.exception;

public class RoomExistsException extends RuntimeException {
    public RoomExistsException(String message) {
        super(message);
    }
}
