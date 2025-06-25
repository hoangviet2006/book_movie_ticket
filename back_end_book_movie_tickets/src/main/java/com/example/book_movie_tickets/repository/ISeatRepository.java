package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeatRepository extends JpaRepository<Seat,Integer> {
    void deleteByRoom_Id(int roomId);
    @Query(value = "select s.* from seat s join room r on r.id=s.room_id where r.id=?1",nativeQuery = true)
    List<Seat> findSeatByRoomId(int id);
}
