package com.example.book_movie_tickets.controller.admin;

import com.example.book_movie_tickets.dto.RoomDetailDto;
import com.example.book_movie_tickets.model.Movie;
import com.example.book_movie_tickets.model.Room;
import com.example.book_movie_tickets.model.Seat;
import com.example.book_movie_tickets.repository.IRoomRepository;
import com.example.book_movie_tickets.service.impl.IMovieService;
import com.example.book_movie_tickets.service.impl.IRoomService;
import com.example.book_movie_tickets.service.impl.ISeatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/room")
public class RoomAdminController {
    @Autowired
    private IRoomService roomService;
    @Autowired
    private ISeatService seatService;

    @GetMapping("")
    public ResponseEntity<?> findAllRoom(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String startSeatCount,
                                         @RequestParam(required = false) String endSeatCount,
                                         @PageableDefault(size = 5) Pageable pageable) {
        Integer start = null;
        Integer end = null;
        String nameR = null;
        try {
            if (startSeatCount != null && !startSeatCount.isEmpty()) {
                start = Integer.parseInt(startSeatCount.trim());
            }
            if (endSeatCount != null && !endSeatCount.isEmpty()) {
                end = Integer.parseInt(endSeatCount.trim());
            }
            if (name != null && !name.isEmpty()) {
                nameR = name.trim();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Thông tin tìm kiếm không hợp lệ");
        }
        Page<Room> roomPage;
        if (nameR != null && start == null && end == null) {
            roomPage = roomService.searchRoomByNamePage(nameR, pageable);
        } else if (nameR == null && start != null && end == null) {
            roomPage = roomService.searchRoomBySeatNumberStartPage(start, pageable);
        } else if (nameR == null && start == null && end != null) {
            roomPage = roomService.searchRoomBySeatNumberEndPage(end, pageable);
        } else if (nameR == null && start != null && end != null) {
            roomPage = roomService.searchRoomBySeatCountPage(start, end, pageable);
        } else if (nameR != null && start != null && end != null) {
            roomPage = roomService.search(name, start, end, pageable);
        } else {
            roomPage = roomService.findAllRoomPage(pageable);
        }
        if (!roomPage.isEmpty()) {
            return ResponseEntity.ok(roomPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }

    @PostMapping("")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            roomService.createRoom(room);
            return ResponseEntity.ok("Thêm mới thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<?> updateRoom(@PathVariable("id") int id,
                                        @RequestBody Room room) {
        try {
            roomService.updateRoom(id, room);
            return ResponseEntity.ok("Chỉnh sửa thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") int id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok("Xoá thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy phim phù hợp");
        }
    }
}
