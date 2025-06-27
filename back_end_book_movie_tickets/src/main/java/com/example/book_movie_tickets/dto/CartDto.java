package com.example.book_movie_tickets.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CartDto {
     Integer getIdBooking();
     String getIdTicket();
     String getTicketPrice();
//     String getStatusBooking();
    LocalDateTime  getBookTicketTime();
     Double getTotalPrice();
     LocalDate getDateShow();
     LocalTime getStartTime();
     LocalTime getEndTime();
     String getNameRoom();
     String getNameCinema();
     String getAddressCinema();
     String getTitleMovie();
     String getDescriptionMovie();
     Integer getDurationMovie();
     String getGenreMovie();
     String getPosterUrl();
     String getSeatCode();
     Integer getIdShow();
     String getSeatId();
     String getStatusTicket();
}
