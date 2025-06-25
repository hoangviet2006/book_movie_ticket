package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Seat;
import com.example.book_movie_tickets.repository.ISeatRepository;
import com.example.book_movie_tickets.service.impl.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private ISeatRepository seatRepository;
    @Override
    public void createSeatForm(Room room) {
        int allSeat=room.getSeatCount();
        int maxSeatRow=20;
        int countSeat=1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < Math.ceil((double) allSeat / maxSeatRow); i++) {
            char row = (char) ('A' + i);
            for (int j = 1; j <=maxSeatRow&&countSeat<=allSeat; j++) {
                    Seat seat = new Seat();
                    seat.setSeatCode(row+String.valueOf(j));
                    seat.setRoom(room);
                    seats.add(seat);
                    countSeat++;
            }
        }
        seatRepository.saveAll(seats); // giống như lặp qua danh sách rồi save từng cái 1
    }

    @Override
    public List<Seat> findSeatByRoomId(int idRoom) {
        return seatRepository.findSeatByRoomId(idRoom);
    }
}
