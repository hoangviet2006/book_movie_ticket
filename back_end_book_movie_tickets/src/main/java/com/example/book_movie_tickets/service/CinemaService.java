package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.exception.CinemaExistsException;
import com.example.book_movie_tickets.model.Cinema;
import com.example.book_movie_tickets.repository.ICinemaRepository;
import com.example.book_movie_tickets.service.impl.ICinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService implements ICinemaService {
    @Autowired
    private ICinemaRepository cinemaRepository;

    @Override
    public Cinema findCinemaById(int id) {
        return cinemaRepository.findCinemaById(id);
    }

    @Override
    public Page<Cinema> findAllCinemaPage(Pageable pageable) {
        return cinemaRepository.findAllCinemaPage(pageable);
    }

    @Override
    public List<Cinema> findAllCinema() {
        return cinemaRepository.findAllCinema();
    }

    @Override
    public void createCinema(Cinema cinema) {
        int count = cinemaRepository.checkCinema(cinema.getName(),cinema.getAddress());
        if (count>0){
            throw new CinemaExistsException("Rạp chiếu phim này đã tồn tại trong hệ thống!");
        }
        Cinema newCinema = new Cinema();
        newCinema.setName(cinema.getName());
        newCinema.setAddress(cinema.getAddress());
        newCinema.setSoftDelete(false);
        cinemaRepository.save(newCinema);
    }

    @Override
    public boolean updateCinema(Integer id, Cinema cinema) {
        Cinema findCinema = cinemaRepository.findCinemaById(id);
        int count = cinemaRepository.checkCinemaUpdate(cinema.getName(),cinema.getAddress(),id);
        if (count>0){
            throw new CinemaExistsException("Rạp chiếu phim này đã tồn tại trong hệ thống!");
        }
        if (findCinema!=null){
            if (cinema.getName()!=null){
                findCinema.setName(cinema.getName());
            }
            if (cinema.getAddress()!=null){
                findCinema.setAddress(cinema.getAddress());
            }
            cinemaRepository.save(findCinema);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCinema(Integer id) {
        Cinema findCinema = cinemaRepository.findCinemaById(id);
        if (findCinema!=null){
            findCinema.setSoftDelete(true);
            cinemaRepository.save(findCinema);
            return true;
        }
        return false;
    }

    @Override
    public List<Cinema> searchCinemaByName(String name) {
        return cinemaRepository.searchCinemaByName("%"+name+"%");
    }

    @Override
    public Page<Cinema> searchCinemaByNamePage(String name, Pageable pageable) {
        return cinemaRepository.searchCinemaByNamePage("%"+name+"%",pageable);
    }

    @Override
    public List<Cinema> searchCinemaByAddress(String address) {
        return cinemaRepository.searchCinemaByAddress("%"+address+"%");
    }

    @Override
    public Page<Cinema> searchCinemaByAddressPage(String address, Pageable pageable) {
        return cinemaRepository.searchCinemaByAddressPage("%"+address+"%",pageable);
    }

    @Override
    public List<Cinema> searchCinemaByNameAndAddress(String name, String address) {
        return cinemaRepository.searchCinemaByNameAndAddress("%"+name+"%","%"+address+"%");
    }

    @Override
    public Page<Cinema> searchCinemaByNameAndAddressPage(String name, String address, Pageable pageable) {
        return cinemaRepository.searchCinemaByNameAndAddressPage("%"+name+"%","%"+address+"%",pageable);
    }
}
