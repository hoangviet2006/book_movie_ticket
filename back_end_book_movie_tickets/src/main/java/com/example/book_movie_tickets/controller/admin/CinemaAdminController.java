package com.example.book_movie_tickets.controller.admin;

import com.example.book_movie_tickets.exception.CinemaExistsException;
import com.example.book_movie_tickets.model.Cinema;
import com.example.book_movie_tickets.service.impl.ICinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cinema")
public class CinemaAdminController {
    @Autowired
    private ICinemaService cinemaService;

    @GetMapping("")
    public ResponseEntity<?> findAllCinema(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String address,
                                           @PageableDefault(size = 5) Pageable pageable) {
        Page<Cinema> cinemaPage;
        if (name != null && address == null) {
            cinemaPage = cinemaService.searchCinemaByNamePage(name, pageable);
        } else if (name == null && address != null) {
            cinemaPage = cinemaService.searchCinemaByAddressPage(address, pageable);
        } else if (name != null && address != null) {
            cinemaPage = cinemaService.searchCinemaByNameAndAddressPage(name, address, pageable);
        } else {
            cinemaPage = cinemaService.findAllCinemaPage(pageable);
        }
        if (!cinemaPage.isEmpty()) {
            return ResponseEntity.ok(cinemaPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }

    @PostMapping("")
    public ResponseEntity<?> createCinema(@RequestBody Cinema cinema) {
        try {
            cinemaService.createCinema(cinema);
            return ResponseEntity.ok("Thêm mới thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCinema(@PathVariable("id") int id,
                                          @RequestBody Cinema cinema) {
        try {
            cinemaService.updateCinema(id, cinema);
            return ResponseEntity.ok("Chỉnh sửa thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable("id") int id) {
        try {
            cinemaService.deleteCinema(id);
            return ResponseEntity.ok("Xoá thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailCinema(@PathVariable("id") int id) {
        Cinema cinema = cinemaService.findCinemaById(id);
        if (cinema != null) {
            return ResponseEntity.ok(cinema);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }

    @ExceptionHandler()
    public ResponseEntity<String> handleCinemaExists(CinemaExistsException cinemaExistsException) {
        return new ResponseEntity<>(cinemaExistsException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
