package com.example.book_movie_tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowBookingInfoDto {
    private Integer idShow;
    private LocalDate dateShow;
    private LocalTime startTime;
    private LocalTime endTime;
    private MovieInfoDto movieInfoDto;
    private RoomInfoDto roomInfoDto;
    private CinemaInfoDto cinemaInfoDto;
    private List<SeatStatusDto> seatStatusDto;
}
