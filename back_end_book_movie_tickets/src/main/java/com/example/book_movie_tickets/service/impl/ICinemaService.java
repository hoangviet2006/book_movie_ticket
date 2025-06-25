package com.example.book_movie_tickets.service.impl;

import com.example.book_movie_tickets.model.Cinema;
import com.example.book_movie_tickets.model.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICinemaService {
    Cinema findCinemaById(int id);
    Page<Cinema> findAllCinemaPage(Pageable pageable);
    List<Cinema> findAllCinema();
    void createCinema(Cinema cinema);
    boolean updateCinema(Integer id,Cinema cinema);
    boolean deleteCinema(Integer id);
    List<Cinema> searchCinemaByName(String name);
    Page<Cinema> searchCinemaByNamePage(String name,Pageable pageable);
    List<Cinema> searchCinemaByAddress(String address);
    Page<Cinema> searchCinemaByAddressPage(String address,Pageable pageable);
    List<Cinema> searchCinemaByNameAndAddress(String name,String address);
    Page<Cinema> searchCinemaByNameAndAddressPage(String name,String address,Pageable pageable);
}
