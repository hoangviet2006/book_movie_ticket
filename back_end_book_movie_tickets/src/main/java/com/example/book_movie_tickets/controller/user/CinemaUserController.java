package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.model.Cinema;
import com.example.book_movie_tickets.service.impl.ICinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/cinema")
public class CinemaUserController {
    @Autowired
    private ICinemaService cinemaService;

    @GetMapping("")
    public ResponseEntity<List<?>> findAllCinema(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String address) {
        List<Cinema> cinemaList;
        if (name != null && address == null) {
            cinemaList = cinemaService.searchCinemaByName(name);
        } else if (name == null && address != null) {
            cinemaList = cinemaService.searchCinemaByAddress(address);
        } else if (name != null && address != null) {
            cinemaList = cinemaService.searchCinemaByNameAndAddress(name, address);
        } else {
            cinemaList = cinemaService.findAllCinema();
        }
        if (!cinemaList.isEmpty()) {
            return ResponseEntity.ok(cinemaList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailCinema(@PathVariable("id") int id) {
        Cinema cinema = cinemaService.findCinemaById(id);
        if (cinema != null) {
            return ResponseEntity.ok(cinema);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
