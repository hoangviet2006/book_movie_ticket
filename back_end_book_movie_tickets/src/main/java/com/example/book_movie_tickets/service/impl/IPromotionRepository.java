package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPromotionRepository extends JpaRepository<Promotion,Integer> {
}
