package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.dto.*;
import com.example.book_movie_tickets.model.Shows;
import com.example.book_movie_tickets.service.impl.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/show")
public class ShowUserController {

    @Autowired
    private IShowService showService;
    @GetMapping("")
    public ResponseEntity<?> findAllShowForDisplay(@RequestParam(required = false) LocalDate date,
                                                   @RequestParam(required = false) LocalTime time) {
        List<ResponseDisplayShowForUser> showsList;
        List<GroupedMovieShowDto> groupedMovieShowDto;
        if (date!=null&&time==null){
            showsList=showService.findMovieShowsForDisplayOnDate(date);
            groupedMovieShowDto=showService.groupMovieForShow(showsList);
        }else if (date==null&&time!=null){
            showsList=showService.findMovieShowsForDisplayOnStartTime(time);
            groupedMovieShowDto=showService.groupMovieForShow(showsList);
        }else if (date!=null&&time!=null){
            showsList=showService.findMovieShowsForDisplayOnDateAndStartTime(date,time);
            groupedMovieShowDto=showService.groupMovieForShow(showsList);
        } else {
            showsList=showService.findAllMovieShowsForDisplay();
            groupedMovieShowDto=showService.groupMovieForShow(showsList);
        }
        if (!groupedMovieShowDto.isEmpty()){
            return ResponseEntity.ok(groupedMovieShowDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detailShow(@PathVariable("id") Integer id){
        Shows shows = showService.detailShowById(id);
        if (shows!=null){
            return ResponseEntity.ok(shows);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("{idShow}/detail-booking")
    public ResponseEntity<?> detailShowOnBooking(@PathVariable("idShow") Integer id){
        List<RequestBookTicket> list = showService.detailShowOnBooking(id);
        ShowBookingInfoDto showBookingInfoDto = showService.groupDetailShowOnBooking(list);
        if (showBookingInfoDto!=null){
            return ResponseEntity.ok(showBookingInfoDto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
