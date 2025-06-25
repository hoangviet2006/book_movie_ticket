package com.example.book_movie_tickets.dto;

import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailDto {
    private Room room;
    private List<Seat> seats;
}
