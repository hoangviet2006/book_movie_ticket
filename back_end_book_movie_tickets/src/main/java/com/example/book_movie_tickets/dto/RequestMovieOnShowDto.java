package com.example.book_movie_tickets.dto;


import java.sql.Time;
import java.util.Date;

public interface RequestMovieOnShowDto {
    Integer getId();
    String getTitleMovie();
    String getPoster();
    Integer getDuration();
    String getDescription();
    String getGenre();
    Double getPrice();
    Date getDateShow();
    Time getStartTime();
    Time getEndTime();
    String getNameRoom();
    Integer getSeatCount();
    String getNameCinema();
    String getAddress();
}
