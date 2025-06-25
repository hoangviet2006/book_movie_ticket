package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoomService {
    Room detailRoomById(int id);
    List<Room> findAllRoom();
    Page<Room> findAllRoomPage(Pageable pageable);
    void createRoom(Room room);
    boolean updateRoom(Integer id,Room room);
    boolean deleteRoom(Integer id);
    List<Room> searchRoomByName(String name);
    Page<Room> searchRoomByNamePage(String name,Pageable pageable);
    List<Room> searchRoomBySeatNumberStart(Integer seatNumberStart);
    Page<Room> searchRoomBySeatNumberStartPage(Integer seatNumberStart,Pageable pageable);
    List<Room> searchRoomBySeatNumberEnd(Integer seatNumberEnd);
    Page<Room> searchRoomBySeatNumberEndPage(Integer seatNumberEnd,Pageable pageable);
    List<Room> searchRoomBySeatCount(Integer seatCountStart,Integer seatCountEnd);
    Page<Room> searchRoomBySeatCountPage(Integer seatCountStart,Integer seatCountEnd,Pageable pageable);
//    List<Room> searchRoomByNameCinema(String nameCinema);
//    Page<Room> searchRoomByNameCinemaPage(String nameCinema,Pageable pageable);
    Page<Room> search(String name,Integer seatCountStart,Integer seatCountEnd,Pageable pageable);
}
