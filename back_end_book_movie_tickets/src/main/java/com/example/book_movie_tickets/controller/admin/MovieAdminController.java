package com.example.book_movie_tickets.controller.admin;

import com.example.book_movie_tickets.exception.MovieExistsException;
import com.example.book_movie_tickets.model.Movie;
import com.example.book_movie_tickets.service.impl.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/movie")
public class MovieAdminController {
    @Autowired
    private IMovieService movieService;

    @GetMapping("")
    public ResponseEntity<?> findAllMovie(@RequestParam(required = false) String title ,
                                                @RequestParam(required = false) String genre,
                                                @PageableDefault(size = 5)Pageable pageable){
        Page<Movie> moviePage;
        if (title!=null&&genre==null){
            moviePage = movieService.searchMovieByTitlePage(title,pageable);
        }else if (title==null&&genre!=null){
            moviePage=movieService.searchMovieByGenrePage(genre,pageable);
        }else if (title!=null&&genre!=null){
            moviePage=movieService.searchMovieByTitleAndGenrePage(title,genre,pageable);
        } else {
            moviePage = movieService.findAllMoviePage(pageable);
        }
        if (!moviePage.isEmpty()){
            return ResponseEntity.ok(moviePage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailMovie(@PathVariable("id") int id){
        Movie movie = movieService.findMovieById(id);
        if (movie!=null){
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim phù hợp");
    }

    @PostMapping("")
    public ResponseEntity<?> createMovie(@RequestBody Movie movie){
        try {
            movieService.createMovie(movie);
            return ResponseEntity.ok("Thêm mới thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") int id,
                                         @RequestBody Movie movie){
        try {
            movieService.updateMovie(id,movie);
            return ResponseEntity.ok("Chỉnh sửa thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") int id){
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.ok("Xoá thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ExceptionHandler(MovieExistsException.class)
    public ResponseEntity<String> handleMovieExists(MovieExistsException movieExistsException){
        return new ResponseEntity<>(movieExistsException.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
