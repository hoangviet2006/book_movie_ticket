package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.dto.RoomDetailDto;
import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Seat;
import com.example.book_movie_tickets.service.impl.IRoomService;
import com.example.book_movie_tickets.service.impl.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/room")
public class RoomUserController {
    @Autowired
    private IRoomService roomService;
    @Autowired
    private ISeatService seatService;
    @GetMapping("")
    public ResponseEntity<List<?>> findAllRoom(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) Integer startSeatCount,
                                               @RequestParam(required = false) Integer endSeatCount) {
        List<Room> roomList;
        if (name!=null&&startSeatCount==null&&endSeatCount==null){
            roomList=roomService.searchRoomByName(name);
        }else if (name==null&&startSeatCount!=null&&endSeatCount==null){
            roomList=roomService.searchRoomBySeatNumberStart(startSeatCount);
        }else if (name==null&&startSeatCount==null&&endSeatCount!=null){
            roomList=roomService.searchRoomBySeatNumberEnd(endSeatCount);
        }else if (name==null&&startSeatCount!=null&&endSeatCount!=null){
            roomList=roomService.searchRoomBySeatCount(startSeatCount,endSeatCount);
        }else {
            roomList=roomService.findAllRoom();
        }
        if (!roomList.isEmpty()){
            return ResponseEntity.ok(roomList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detailRoom(@PathVariable("id") int id) {
        try {
            Room room = roomService.detailRoomById(id);
            List<Seat> seats = seatService.findSeatByRoomId(room.getId());
            RoomDetailDto roomDetailDto = new RoomDetailDto();
            roomDetailDto.setRoom(room);
            roomDetailDto.setSeats(seats);
            return ResponseEntity.ok(roomDetailDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
