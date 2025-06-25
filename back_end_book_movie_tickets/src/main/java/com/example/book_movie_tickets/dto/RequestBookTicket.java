package com.example.book_movie_tickets.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RequestBookTicket {
    Integer getIdShow();
    LocalDate getDateShow();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getTitleMovie();
    String getDescription();
    Integer getDuration();
    String getGenreMovie();
    String getUrlMovie();
    Double getPriceMovie();
    String getNameRoom();
    Integer getSeatCount();
    Integer getSeatId();
    String getSeatCode();
    String getStatusTicket();
    String getNameCinema();
    String getAddressCinema();
}
