package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.dto.RequestBookTicket;
import com.example.book_movie_tickets.dto.ResponseDisplayShowForUser;
import com.example.book_movie_tickets.model.Shows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface IShowRepository extends JpaRepository<Shows, Integer> {
    Shows findShowsById(int id);

    @Query(value = "select count(*) from shows s \n" +
                   "join room r on s.room_id=r.id \n" +
                   "join movie m on m.id=s.movie_id  \n" +
                   "where s.soft_delete = false and s.date_show=?1 and r.id=?2 and(\n" +
                   "(?3 between s.start_time and s.end_time) or\n" +
                   "(?4 between s.start_time and s.end_time) or\n" +
                   "(s.start_time between ?3 and ?4)\n" +
                   ")", nativeQuery = true)
    int checkShow(LocalDate dateShow, int idRoom, LocalTime startTime, LocalTime endTime);

    @Query(value = "select count(*) from shows s \n" +
                   "join room r on s.room_id=r.id \n" +
                   "join movie m on m.id=s.movie_id  \n" +
                   "where s.soft_delete = false and s.date_show=?1 and r.id=?2 and(\n" +
                   "(?3 between s.start_time and s.end_time) or\n" +
                   "(?4 between s.start_time and s.end_time) or\n" +
                   "(s.start_time between ?3 and ?4)) and s.id !=?5", nativeQuery = true)
    int checkShowUpdate(LocalDate dateShow, int idRoom, LocalTime startTime, LocalTime endTime, int idShow);

    @Query(value = "select * from shows s where s.soft_delete = false", nativeQuery = true)
    List<Shows> findAllShow();

    @Query(value = "select * from shows s where s.soft_delete = false", nativeQuery = true,
            countQuery = "select * from shows s where s.soft_delete = false")
    Page<Shows> findAllShowPage(Pageable pageable);

    @Query(value = "select * from shows s where s.soft_delete = false and s.date_show = ?1", nativeQuery = true)
    List<Shows> searchShowByDateShow(LocalDate date);

    @Query(value = "select * from shows s where s.soft_delete = false and s.date_show = ?1", nativeQuery = true,
            countQuery = "select * from shows s where s.soft_delete = false and s.date_show = ?1")
    Page<Shows> searchShowByDateShowPage(Date date, Pageable pageable);

    @Query(value = "select * from shows s where s.soft_delete = false and s.start_time = ?1", nativeQuery = true)
    List<Shows> searchShowByStartTime(LocalTime time);

    @Query(value = "select * from shows s where s.soft_delete = false and s.start_time = ?1", nativeQuery = true,
            countQuery = "select * from shows s where s.soft_delete = false and s.start_time = ?1")
    Page<Shows> searchShowByStartTimePage(Time time, Pageable pageable);

    @Query(value = "select * from shows s where s.soft_delete = false and s.date_show = ?1 and s.start_time = ?2", nativeQuery = true,
            countQuery = "select * from shows s where s.soft_delete = false and s.date_show = ?1 and s.start_time = ?2")
    Page<Shows> searchShowByDateAndStartTimePage(Date date, Time time, Pageable pageable);

    @Query(value = "     select s.id as idShow,\n" +
                   "           s.date_show as dateShow,\n" +
                   "           s.start_time as startTime,\n" +
                   "           s.end_time as endTime,\n" +
                   "           m.id as idMovie,\n" +
                   "           m.title as titleMovie,\n" +
                   "           m.description as description,\n" +
                   "           m.duration as duration,\n" +
                   "           m.genre as genre,\n" +
                   "           m.price as price,\n" +
                   "           m.poster_url as url,\n" +
                   "           r.name as nameRoom,\n" +
                   "           r.seat_count as seatCount,\n" +
                   "           c.name as nameCinema,\n" +
                   "           c.address as address from shows s \n" +
                   "    join movie m on m.id=s.movie_id \n" +
                   "    join room r on r.id=s.room_id \n" +
                   "    join cinema c on c.id=r.cinema_id\n" +
                   "    where\n" +
                   "     s.date_show =?1 and s.start_time=?2\n" +
                   "     and s.soft_delete=false " +
                   "     and m.soft_delete=false " +
                   "     and r.soft_delete=false " +
                   "     and c.soft_delete=false" +
                   "     AND STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()" +
                   "     order by s.date_show, s.start_time", nativeQuery = true)
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDateAndStartTime(LocalDate date, LocalTime time);

    @Query(value = "     select s.id as idShow,\n" +
                   "           s.date_show as dateShow,\n" +
                   "           s.start_time as startTime,\n" +
                   "           s.end_time as endTime,\n" +
                   "           m.id as idMovie,\n" +
                   "           m.title as titleMovie,\n" +
                   "           m.description as description,\n" +
                   "           m.duration as duration,\n" +
                   "           m.genre as genre,\n" +
                   "           m.price as price,\n" +
                   "           m.poster_url as url,\n" +
                   "           r.name as nameRoom,\n" +
                   "           r.seat_count as seatCount,\n" +
                   "           c.name as nameCinema,\n" +
                   "           c.address as address from shows s \n" +
                   "    join movie m on m.id=s.movie_id \n" +
                   "    join room r on r.id=s.room_id \n" +
                   "    join cinema c on c.id=r.cinema_id\n" +
                   "    where\n" +
                   "    s.date_show =?1 " +
                   "    and s.soft_delete=false " +
                   "    and m.soft_delete=false " +
                   "    and r.soft_delete=false " +
                   "    and c.soft_delete=false " +
                   "    AND STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()" +
                   "    order by s.date_show, s.start_time", nativeQuery = true)
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDate(LocalDate date);

    @Query(value = "     select s.id as idShow,\n" +
                   "           s.date_show as dateShow,\n" +
                   "           s.start_time as startTime,\n" +
                   "           s.end_time as endTime,\n" +
                   "           m.id as idMovie,\n" +
                   "           m.title as titleMovie,\n" +
                   "           m.description as description,\n" +
                   "           m.duration as duration,\n" +
                   "           m.genre as genre,\n" +
                   "           m.price as price,\n" +
                   "           m.poster_url as url,\n" +
                   "           r.name as nameRoom,\n" +
                   "           r.seat_count as seatCount,\n" +
                   "           c.name as nameCinema,\n" +
                   "           c.address as address from shows s \n" +
                   "    join movie m on m.id=s.movie_id \n" +
                   "    join room r on r.id=s.room_id \n" +
                   "    join cinema c on c.id=r.cinema_id\n" +
                   "    where\n" +
                   "    s.start_time=?1\n" +
                   "    and s.soft_delete=false " +
                   "    and m.soft_delete=false " +
                   "    and r.soft_delete=false " +
                   "    and c.soft_delete=false  " +
                   "    AND STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()" +
                   "    order by s.date_show, s.start_time", nativeQuery = true)
    List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnStartTime(LocalTime time);

    @Query(value = "SELECT \n" +
                   "    s.id AS idShow,\n" +
                   "    s.date_show AS dateShow,\n" +
                   "    s.start_time AS startTime,\n" +
                   "    s.end_time AS endTime,\n" +
                   "    m.id AS idMovie,\n" +
                   "    m.title AS titleMovie,\n" +
                   "    m.description AS description,\n" +
                   "    m.duration AS duration,\n" +
                   "    m.genre AS genre,\n" +
                   "    m.price AS price,\n" +
                   "    m.poster_url AS url,\n" +
                   "    r.name AS nameRoom,\n" +
                   "    r.seat_count AS seatCount,\n" +
                   "    c.name AS nameCinema,\n" +
                   "    c.address AS address\n" +
                   "FROM shows s \n" +
                   "JOIN movie m ON m.id = s.movie_id \n" +
                   "JOIN room r ON r.id = s.room_id \n" +
                   "JOIN cinema c ON c.id = r.cinema_id\n" +
                   "where \n" +
                   "    s.soft_delete = FALSE \n" +
                   "    and m.soft_delete = FALSE \n" +
                   "    and r.soft_delete = FALSE \n" +
                   "    and c.soft_delete = FALSE\n" +
                   "    AND STR_TO_DATE(CONCAT(s.date_show, ' ', s.end_time), '%Y-%m-%d %H:%i:%s') > NOW()" +
                   "    order by s.date_show, s.start_time", nativeQuery = true)
    List<ResponseDisplayShowForUser> findAllMovieShowsForDisplay();

    // detail show
    @Query(value = "  select sh.id as idShow,\n" +
                   "  sh.date_show as dateShow,\n" +
                   "  sh.start_time as startTime,\n" +
                   "  sh.end_time as endTime,\n" +
                   "  m.title as titleMovie,\n" +
                   "  m.description as description,\n" +
                   "  m.duration as duration,\n" +
                   "  m.genre as genreMovie,\n" +
                   "  m.poster_url as urlMovie,\n" +
                   "  m.price as priceMovie,\n" +
                   "  r.name as nameRoom,\n" +
                   "  r.seat_count as seatCount,\n" +
                   "  s.id as seatId,\n" +
                   "  s.seat_code as seatCode,\n" +
                   "  t.status as statusTicket,\n" +
                   "  c.name as nameCinema,\n" +
                   "  c.address addressCinema\n" +
                   "  from shows sh \n" +
                   "  join movie m on m.id=sh.movie_id\n" +
                   "  join room r on r.id=sh.room_id \n" +
                   "  join seat s on r.id=s.room_id \n" +
                   "  left join ticket t on t.seat_id=s.id\n" +
                   "  and t.booking_id in (select b.id from booking b where b.show_id=sh.id)\n" +
                   "  right join cinema c on c.id=r.cinema_id\n" +
                   "  where sh.soft_delete=false\n" +
                   "  and m.soft_delete=false \n" +
                   "  and r.soft_delete=false\n" +
                   "  and c.soft_delete=false\n" +
                   "  AND STR_TO_DATE(CONCAT(sh.date_show, ' ', sh.end_time), '%Y-%m-%d %H:%i:%s') > NOW() \n"+
                   "  and sh.id=?1\n" +
                   "ORDER BY sh.date_show, sh.start_time", nativeQuery = true)
    List<RequestBookTicket> detailShowOnBooking(Integer idShow);

}
