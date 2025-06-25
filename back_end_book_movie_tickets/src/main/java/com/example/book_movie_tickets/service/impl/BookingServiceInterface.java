package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.dto.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface BookingServiceInterface {
    boolean bookTickets(BookingRequest bookingRequest, HttpServletRequest httpRequest);
}
