package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Seat;

import java.util.List;

public interface ISeatService {
    void createSeatForm(Room room);
    List<Seat> findSeatByRoomId(int idRoom);
}
