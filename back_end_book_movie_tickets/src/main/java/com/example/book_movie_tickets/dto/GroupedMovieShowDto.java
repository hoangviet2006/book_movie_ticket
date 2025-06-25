package com.example.book_movie_tickets.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupedMovieShowDto {
    private Integer idMovie;
    private String titleMovie;
    private String url;
    private Integer duration;
    private String description;
    private String genre;
    private Double price;
    private String nameCinema;
    private String address;
    private Integer seatCount;
    private List<ShowtimeDto> showtime;
}
