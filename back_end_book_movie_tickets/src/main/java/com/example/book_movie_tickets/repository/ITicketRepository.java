package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.dto.TicketMinimal;
import com.example.book_movie_tickets.model.Seat;
import com.example.book_movie_tickets.model.Shows;
import com.example.book_movie_tickets.model.Ticket;
import jakarta.transaction.Transactional;
import jdk.dynalink.linker.LinkerServices;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
@Transactional
@Repository
public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByTxnRef(String txnRef);


    void deleteBySeat_IdAndBooking_Shows_IdAndStatus(int seatId, int bookingShowsId, Ticket.Status status);
    @Query(value = "select count(t.seat_id) from ticket t \n" +
                   "join booking b on t.booking_id=b.id\n" +
                   "where t.seat_id=?1 and b.show_id=?2 and b.user_id != ?3 and t.status in('PAID','UNPAID')" ,nativeQuery = true)
    int countTicketBySeat(int idSeat,int idShow,int idUser);
    @Query(value = "select s.seat_code from ticket t \n" +
                   "join booking b on t.booking_id=b.id\n" +
                   "join seat s on s.id=t.seat_id\n" +
                   "where t.seat_id in (?1) and b.show_id=?2 and t.status in('PAID','UNPAID')",nativeQuery = true)
    List<String> findSeatCodeByTicket(List<Integer> idSeat, int idShow);
    @Query(value = "select t.id, t.book_ticket_time from ticket t \n" +
                   "where t.status ='UNPAID'",nativeQuery = true)
    List<TicketMinimal> findTicketOnClose();
}
