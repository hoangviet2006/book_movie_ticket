package com.example.book_movie_tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoDto {
    private String titleMovie;
    private String description;
    private Integer duration;
    private String genreMovie;
    private String urlMovie;
    private Double priceMovie;
}
