package com.example.book_movie_tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatStatusDto {
    private Integer seatId;
    private String seatCode;
    private String seatStatus;
}
