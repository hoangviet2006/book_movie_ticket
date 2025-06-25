package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface IOtpRepository extends JpaRepository<OtpCode,Integer> {
    Optional<OtpCode> findByEmail(String email);
    void deleteByEmail(String email);
    void deleteByCode(String code);
}
