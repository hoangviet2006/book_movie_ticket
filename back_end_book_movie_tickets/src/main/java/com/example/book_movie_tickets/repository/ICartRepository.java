package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.dto.CartDto;
import com.example.book_movie_tickets.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Booking,Integer> {

    @Query(value = "select b.id as idBooking,\n" +
                   "  group_concat(distinct t.id) as idTicket," +
                   " group_concat(t.price) as ticketPrice," +
                   "       b.status as statusBooking,\n" +
                   "       b.total_price as totalPrice,\n" +
                   "       max(t.book_ticket_time) as bookTicketTime," +
                   "       s.date_show as dateShow,\n" +
                   "       s.start_time as start_time,\n" +
                   "       s.end_time as endTime,\n" +
                   "       r.name as nameRoom,\n" +
                   "       c.name as nameCinema,\n" +
                   "       c.address as addressCinema,\n" +
                   "       m.title as titleMovie,\n" +
                   "       m.description as descriptionMovie,\n" +
                   "       m.duration as durationMovie,\n" +
                   "       m.genre as genreMovie,\n" +
                   "       m.poster_url as posterUrl,\n" +
                   "       group_concat(distinct se.seat_code) as seatCode,\n" +
                   "       s.id as idShow,\n" +
                   "       group_concat(distinct se.id) as seatId \n" +
                   "from booking b \n" +
                   "join ticket t on t.booking_id=b.id\n" +
                   "join shows s on s.id=b.show_id\n" +
                   "join room r on r.id=s.room_id\n" +
                   "join movie m on m.id=s.movie_id\n" +
                   "join cinema c on c.id=r.cinema_id\n" +
                   "join seat se on se.id=t.seat_id\n" +
                   "join user u on u.id=b.user_id\n" +
                   "where u.username=?1\n" +
                   "and t.status != 'CANCELLED'\n" +
                   "group by b.id \n" +
                   "order by case\n" +
                   "when b.status='UNPAID' then 0 \n" + // 0 hiển thị trước
                   "when b.status='PAID' then 1\n" +    // 1 hiển thị sau
                   "else 2\n" +                         // else 2 là các trường hợp khác
                   "end,\n" +                           // kết thúc case
                   "b.id desc",nativeQuery = true)
    List<CartDto> findCartByUserName(String username);
}
