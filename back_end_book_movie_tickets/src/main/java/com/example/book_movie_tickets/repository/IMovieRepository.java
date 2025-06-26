package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.dto.RequestMovieOnShowDto;
import com.example.book_movie_tickets.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IMovieRepository extends JpaRepository<Movie,Integer> {
    @Query(value = "select count(*) from movie m where m.soft_delete = false and m.title=?1 and m.release_date=?2",nativeQuery = true)
    int checkMovie(String title, LocalDate date);

    @Query(value = "select count(*) from movie m where m.soft_delete = false and m.title=?1 and m.release_date=?2 and m.id!=?3",nativeQuery = true)
    int checkMovieUpdate(String title, LocalDate date, int id);

    Movie findMovieById(int id);

    @Query(value = "select * from movie m where m.soft_delete= false",nativeQuery = true)
    List<Movie>  findAllMovie();

    @Query(value = "select * from movie m where m.soft_delete= false",nativeQuery = true,
           countQuery = "select * from movie m where m.soft_delete= false")
    Page<Movie> findAllMoviePage(Pageable pageable);

    @Query(value = "select * from movie m where m.soft_delete= false and m.title like ?1",nativeQuery = true)
    List<Movie> searchMovieByTitle(String title);

    @Query(value = "select * from movie m where m.soft_delete= false and m.title like ?1",nativeQuery = true,
                    countQuery = "select * from movie m where m.soft_delete= false and m.title like ?1")
    Page<Movie> searchMovieByTitlePage(String title,Pageable pageable);

    @Query(value = "select * from movie m where m.soft_delete= false and m.genre like ?1",nativeQuery = true)
    List<Movie> searchMovieByGenre(String genre);

    @Query(value = "select * from movie m where m.soft_delete= false and m.genre like ?1",nativeQuery = true,
                    countQuery = "select * from movie m where m.soft_delete= false and m.genre like ?1")
    Page<Movie> searchMovieByGenrePage(String genre,Pageable pageable);

    @Query(value = "select * from movie m where m.soft_delete= false and (m.title like ?1 and m.genre like ?2)",nativeQuery = true)
    List<Movie> searchByTitleAndGenre(String title,String genre);

    @Query(value = "select * from movie m where m.soft_delete= false and (m.title like ?1 and m.genre like ?2)",nativeQuery = true,
                    countQuery = "select * from movie m where m.soft_delete= false and (m.title like ?1 and m.genre like ?2)")
    Page<Movie> searchByTitleAndGenrePage(String title,String genre,Pageable pageable);

    @Query(value = "select s.id,\n" +
                   "m.title as titleMovie,\n" +
                   "m.poster_url as poster ,\n" +
                   "m.duration as duration,\n" +
                   "m.description as description,\n" +
                   "m.genre as genre,\n" +
                   "m.price as price,\n" +
                   "s.date_show as dateShow,\n" +
                   "s.start_time as startTime,\n" +
                   "s.end_time as endTime,\n" +
                   "r.name as nameRoom,\n" +
                   "r.seat_count as searCount,\n" +
                   "c.name as nameCinema,\n" +
                   "c.address as address \n" +
                   "from shows s \n" +
                   "join movie m on m.id=s.movie_id \n" +
                   "join room r on r.id=s.room_id\n" +
                   "join cinema c on c.id=r.cinema_id where m.id = ?1 and s.soft_delete = false\n" +
                   "and r.soft_delete = false and m.soft_delete=false \n" +
                   "and c.soft_delete = false \n" +
                   "and STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()",nativeQuery = true) // AND STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()
    List<RequestMovieOnShowDto> detailShowOnIdMovie(int idMovie);

}
