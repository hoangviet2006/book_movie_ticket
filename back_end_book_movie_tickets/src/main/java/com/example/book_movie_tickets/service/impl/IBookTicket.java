package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.dto.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IBookTicket {
    List<Integer> bookTickets(BookingRequest bookingRequest, HttpServletRequest httpRequest);

}
