package com.example.book_movie_tickets.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ResponseDisplayShowForUser {
    Integer getIdShow();
    Integer getIdMovie();
    String getTitleMovie();
    String getUrl();
    Integer getDuration();
    String getDescription();
    String getGenre();
    Double getPrice();
    LocalDate getDateShow();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getNameRoom();
    Integer getSeatCount();
    String getNameCinema();
    String getAddress();
}
