package com.example.book_movie_tickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int duration;
    private String genre;
    private double price;
    @Column(name = "soft_delete")
    private boolean softDelete;
    @Column(name = "poster_url",columnDefinition = "TEXT")
    private String posterUrl;
    @Column(name = "release_date")
    private LocalDate releaseDate;
}
