package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking,Integer> {
}
