package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.dto.RequestMovieOnShowDto;
import com.example.book_movie_tickets.model.Movie;
import com.example.book_movie_tickets.repository.IMovieRepository;
import com.example.book_movie_tickets.service.impl.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/movie")
public class MovieUserController {
    @Autowired
    private IMovieService movieService;
    @Autowired
    private IMovieRepository movieRepository;
    @GetMapping("")
    public ResponseEntity<List<?>> findAllMovie(@RequestParam(required = false) String title ,
                                                @RequestParam(required = false) String genre){
        List<Movie> movieList;
        if (title!=null&&genre==null){
             movieList = movieService.searchMovieByTitle(title);
        }else if (title==null&&genre!=null){
            movieList=movieService.searchMovieByGenre(genre);
        }else if (title!=null&&genre!=null){
            movieList=movieService.searchMovieByTitleAndGenre(title,genre);
        } else {
            movieList = movieService.findAllMovie();
        }
        if (!movieList.isEmpty()){
            return ResponseEntity.ok(movieList);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detailMovie(@PathVariable("id") int id){
        Movie movie = movieService.findMovieById(id);
        if (movie!=null){
            return ResponseEntity.ok(movie);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("{id}/detail")
    public ResponseEntity<?> detailShowOnIdMovie(@PathVariable("id") int id){
        List<RequestMovieOnShowDto> movie =  movieRepository.detailShowOnIdMovie(id);
        if (movie!=null){
            return ResponseEntity.ok(movie);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
