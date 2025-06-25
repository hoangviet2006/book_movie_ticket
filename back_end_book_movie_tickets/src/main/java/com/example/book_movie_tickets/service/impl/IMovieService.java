package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMovieService {
    List<Movie> findAllMovie();
    Page<Movie> findAllMoviePage(Pageable pageable);
    void createMovie(Movie movie);
    boolean updateMovie(Integer id,Movie movie);
    boolean deleteMovie(Integer id);
    Movie findMovieById(Integer id);
    List<Movie> searchMovieByTitle(String title);
    Page<Movie> searchMovieByTitlePage(String title,Pageable pageable);
    List<Movie> searchMovieByGenre(String genre);
    Page<Movie> searchMovieByGenrePage(String genre,Pageable pageable);
    List<Movie> searchMovieByTitleAndGenre(String title,String genre);
    Page<Movie> searchMovieByTitleAndGenrePage(String title,String genre,Pageable pageable);
}
