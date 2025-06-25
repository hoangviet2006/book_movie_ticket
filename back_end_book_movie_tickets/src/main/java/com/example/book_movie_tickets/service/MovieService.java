package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.exception.MovieExistsException;
import com.example.book_movie_tickets.model.Movie;
import com.example.book_movie_tickets.repository.IMovieRepository;
import com.example.book_movie_tickets.service.impl.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private IMovieRepository movieRepository;
    @Override
    public List<Movie> findAllMovie() {
        return movieRepository.findAllMovie();
    }

    @Override
    public Page<Movie> findAllMoviePage(Pageable pageable) {
        return movieRepository.findAllMoviePage(pageable);
    }

    @Override
    public void createMovie(Movie movie) {
        int count = movieRepository.checkMovie(movie.getTitle(),movie.getReleaseDate());
        if (count>0){
           throw new MovieExistsException("Bộ phim này đã tồn tại trong hệ thống");
        }
        Movie newMovie = new Movie();
        newMovie.setDescription(movie.getDescription());
        newMovie.setDuration(movie.getDuration());
        newMovie.setGenre(movie.getGenre());
        newMovie.setPrice(movie.getPrice());
        newMovie.setPosterUrl(movie.getPosterUrl());
        newMovie.setReleaseDate(movie.getReleaseDate());
        newMovie.setTitle(movie.getTitle());
        newMovie.setSoftDelete(false);
        movieRepository.save(newMovie);
    }

    @Override
    public boolean updateMovie(Integer id, Movie movie) {
        Optional<Movie> findMovie = movieRepository.findById(id);
        if (movie.getTitle() != null && movie.getReleaseDate() != null) {
            int count = movieRepository.checkMovieUpdate(movie.getTitle(),movie.getReleaseDate(),id);
            if (count>0){
                throw new  MovieExistsException("Bộ phim này đã tồn tại trong hệ thống");
            }
        }
        if (findMovie.isPresent()){
            if (movie.getTitle()!=null){
                findMovie.get().setTitle(movie.getTitle());
            }
            if (findMovie.get().getDuration()!=movie.getDuration()){
                findMovie.get().setDuration(movie.getDuration());
            }
            if (movie.getGenre()!=null){
                findMovie.get().setGenre(movie.getGenre());
            }
            if (findMovie.get().getPrice()!=movie.getPrice()){
                findMovie.get().setPrice(movie.getPrice());
            }
            if (movie.getPosterUrl()!=null){
                findMovie.get().setPosterUrl(movie.getPosterUrl());
            }
            if (movie.getReleaseDate()!=null){
                findMovie.get().setReleaseDate(movie.getReleaseDate());
            }
            if (movie.getDescription()!=null){
                findMovie.get().setDescription(movie.getDescription());
            }
            movieRepository.save(findMovie.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMovie(Integer id) {
        Optional<Movie> findMovie = movieRepository.findById(id);
        if (findMovie.isPresent()){
            findMovie.get().setSoftDelete(true);
            movieRepository.save(findMovie.get());
            return true;
        }
        return false;
    }

    @Override
    public Movie findMovieById(Integer id) {
        Movie movie = movieRepository.findMovieById(id);
        if (movie!=null){
            return movie;
        }
        return null;
    }


    @Override
    public List<Movie> searchMovieByTitle(String title) {
        return movieRepository.searchMovieByTitle("%"+title+"%");
    }

    @Override
    public Page<Movie> searchMovieByTitlePage(String title, Pageable pageable) {
        return movieRepository.searchMovieByTitlePage("%"+title+"%",pageable);
    }

    @Override
    public List<Movie> searchMovieByGenre(String genre) {
        return movieRepository.searchMovieByGenre("%"+genre+"%");
    }

    @Override
    public Page<Movie> searchMovieByGenrePage(String genre, Pageable pageable) {
        return movieRepository.searchMovieByGenrePage("%"+genre+"%",pageable);
    }

    @Override
    public List<Movie> searchMovieByTitleAndGenre(String title, String genre) {
        return movieRepository.searchByTitleAndGenre("%"+title+"%","%"+genre+"%");
    }

    @Override
    public Page<Movie> searchMovieByTitleAndGenrePage(String title, String genre, Pageable pageable) {
        return movieRepository.searchByTitleAndGenrePage("%"+title+"%","%"+genre+"%",pageable);
    }
}
