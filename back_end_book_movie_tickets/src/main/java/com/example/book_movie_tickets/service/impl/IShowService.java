package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.dto.GroupedMovieShowDto;
import com.example.book_movie_tickets.dto.RequestBookTicket;
import com.example.book_movie_tickets.dto.ResponseDisplayShowForUser;
import com.example.book_movie_tickets.dto.ShowBookingInfoDto;
import com.example.book_movie_tickets.model.Shows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface IShowService {
    Shows detailShowById(Integer id);
    List<Shows> findAllShow();
    Page<Shows> findAllShowPage(Pageable pageable);
    void createShow(Shows shows);
    boolean updateShow(Integer id,Shows shows);
    boolean deleteShow(Integer id);
    List<Shows> searchByDateShow(LocalDate date);
    Page<Shows> searchByDateShowPage(Date date, Pageable pageable);
    List<Shows> searchByStartTime(LocalTime time);
    Page<Shows> searchByStartTimePage(Time time,Pageable pageable);
    Page<Shows> searchByDateAndStartTimePage(Date date,Time time,Pageable pageable);
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDateAndStartTime(LocalDate date, LocalTime time);
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDate(LocalDate date);
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnStartTime(LocalTime time);
    List<ResponseDisplayShowForUser> findAllMovieShowsForDisplay();
    List<GroupedMovieShowDto> groupMovieForShow(List<ResponseDisplayShowForUser> list);
    List<RequestBookTicket> detailShowOnBooking(Integer idShow);
    ShowBookingInfoDto groupDetailShowOnBooking(List<RequestBookTicket> list);
}
