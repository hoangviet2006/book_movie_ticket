package com.example.book_movie_tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDto {
    private Integer idShow;
    private LocalDate dateShow;
    private LocalTime startTime;
    private LocalTime endTime;
    private String nameRoom;
}
