package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.exception.RoomExistsException;
import com.example.book_movie_tickets.model.Cinema;
import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.repository.ICinemaRepository;
import com.example.book_movie_tickets.repository.IRoomRepository;
import com.example.book_movie_tickets.repository.ISeatRepository;
import com.example.book_movie_tickets.service.impl.IRoomService;
import com.example.book_movie_tickets.service.impl.ISeatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private ISeatService seatService;
    @Autowired
    private ISeatRepository seatRepository;

    @Override
    public Room detailRoomById(int id) {
        return roomRepository.findRoomById(id);
    }

    @Override
    public List<Room> findAllRoom() {
        return roomRepository.findAllRoom();
    }

    @Override
    public Page<Room> findAllRoomPage(Pageable pageable) {
        return roomRepository.findAllRoomPage(pageable);
    }

    @Override
    public void createRoom(Room room) {
        int count = roomRepository.checkRoom(room.getName(), room.getCinema().getId());
        if (count > 0) {
            throw new RoomExistsException("Phòng này đã tồn tại trong hệ thống");
        }
        Room newRoom = new Room();
        newRoom.setName(room.getName());
        newRoom.setSeatCount(room.getSeatCount());
        if (room.getCinema() != null) {
            newRoom.setCinema(room.getCinema());
        }
        newRoom.setSoftDelete(false);
        Room saveRoom = roomRepository.save(newRoom);
        seatService.createSeatForm(saveRoom);
    }

    @Override
    @Transactional
    public boolean updateRoom(Integer id, Room room) {
        Room findRoom = roomRepository.findRoomById(id);
        int count = roomRepository.checkRoomUpdate(id, room.getName(), room.getCinema().getId());
        if (count > 0) {
            throw new RoomExistsException("Phòng này đã tồn tại trong hệ thống");
        }
        if (findRoom != null) {

            if (room.getName() != null) {
                findRoom.setName(room.getName());
            }
            if (room.getCinema() != null) {
                findRoom.setCinema(room.getCinema());
            }
            if (room.getSeatCount() != findRoom.getSeatCount()) {
                seatRepository.deleteByRoom_Id(id);
                findRoom.setSeatCount(room.getSeatCount());
            }
            Room saveRoom = roomRepository.save(findRoom);
            seatService.createSeatForm(saveRoom);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRoom(Integer id) {
        Room findRoom = roomRepository.findRoomById(id);
        if (findRoom != null) {
            findRoom.setSoftDelete(true);
            roomRepository.save(findRoom);
            return true;
        }
        return false;
    }

    @Override
    public List<Room> searchRoomByName(String name) {
        return roomRepository.searchRoomByName("%" + name + "%");
    }

    @Override
    public Page<Room> searchRoomByNamePage(String name, Pageable pageable) {
        return roomRepository.searchRoomByNamePage("%" + name + "%", pageable);
    }

    @Override
    public List<Room> searchRoomBySeatNumberStart(Integer seatNumberStart) {
        return roomRepository.searchRoomBySeatNumberStart(seatNumberStart);
    }

    @Override
    public Page<Room> searchRoomBySeatNumberStartPage(Integer seatNumberStart, Pageable pageable) {
        return roomRepository.searchRoomBySeatNumberStartPage(seatNumberStart, pageable);
    }

    @Override
    public List<Room> searchRoomBySeatNumberEnd(Integer seatNumberEnd) {
        return roomRepository.searchRoomBySeatNumberEnd(seatNumberEnd);
    }

    @Override
    public Page<Room> searchRoomBySeatNumberEndPage(Integer seatNumberEnd, Pageable pageable) {
        return roomRepository.searchRoomBySeatNumberEndPage(seatNumberEnd, pageable);
    }

    @Override
    public List<Room> searchRoomBySeatCount(Integer seatCountStart, Integer seatCountEnd) {
        return roomRepository.searchRoomBySeatNumber(seatCountStart, seatCountEnd);
    }

    @Override
    public Page<Room> searchRoomBySeatCountPage(Integer seatCountStart, Integer seatCountEnd, Pageable pageable) {
        return roomRepository.searchRoomBySeatNumberPage(seatCountStart, seatCountEnd, pageable);
    }


    @Override
    public Page<Room> search(String name, Integer seatCountStart, Integer seatCountEnd, Pageable pageable) {
        return roomRepository.search(name, seatCountStart, seatCountEnd, pageable);
    }
}
