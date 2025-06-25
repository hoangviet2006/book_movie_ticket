package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.dto.*;
import com.example.book_movie_tickets.exception.ShowExistsException;
import com.example.book_movie_tickets.model.Shows;
import com.example.book_movie_tickets.repository.IShowRepository;
import com.example.book_movie_tickets.service.impl.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ShowService implements IShowService {
    @Autowired
    private IShowRepository showRepository;

    @Override
    public Shows detailShowById(Integer id) {
        return showRepository.findShowsById(id);
    }

    @Override
    public List<Shows> findAllShow() {
        return showRepository.findAllShow();
    }

    @Override
    public Page<Shows> findAllShowPage(Pageable pageable) {
        return showRepository.findAllShowPage(pageable);
    }

    @Override
    public void createShow(Shows shows) {
        int count = showRepository.checkShow(shows.getDateShow(), shows.getRoom().getId(), shows.getStartTime(), shows.getEndTime());
        if (count > 0) {
            throw new ShowExistsException("Xuất chiếu của bộ phim này đã tồn tại trong hệ thống hoặc bị trùng thời gian!");
        }
        Shows newShow = new Shows();
        newShow.setDateShow(shows.getDateShow());
        newShow.setStartTime(shows.getStartTime());
        newShow.setEndTime(shows.getEndTime());
        newShow.setRoom(shows.getRoom());
        newShow.setMovie(shows.getMovie());
        newShow.setSoftDelete(false);
        showRepository.save(newShow);
    }

    @Override
    public boolean updateShow(Integer id, Shows shows) {
        Shows findShow = showRepository.findShowsById(id);
        boolean isRoomChange = shows.getRoom().getId() != findShow.getRoom().getId();
        boolean isTimeChange = !shows.getStartTime().equals(findShow.getStartTime()) ||
                               !shows.getEndTime().equals(findShow.getEndTime()) ||
                               !shows.getDateShow().equals(findShow.getDateShow());
        if (isRoomChange || isTimeChange) {
            int count = showRepository.checkShowUpdate(shows.getDateShow(), shows.getRoom().getId(), shows.getStartTime(), shows.getEndTime(), id);
            if (count > 0) {
                throw new ShowExistsException("Xuất chiếu của bộ phim này đã tồn tại trong hệ thống hoặc bị trùng thời gian!");
            }
        }
        if (findShow != null) {
            if (!shows.getDateShow().equals(findShow.getDateShow())) {
                findShow.setDateShow(shows.getDateShow());
            }
            if (!shows.getStartTime().equals(findShow.getStartTime())) {
                findShow.setStartTime(shows.getStartTime());
            }
            if (!shows.getEndTime().equals(findShow.getEndTime())) {
                findShow.setEndTime(shows.getEndTime());
            }
            if (shows.getRoom().getId() != findShow.getRoom().getId()) {
                findShow.setRoom(shows.getRoom());
            }
            if (shows.getMovie().getId() != findShow.getMovie().getId()) {
                findShow.setMovie(shows.getMovie());
            }
            showRepository.save(findShow);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteShow(Integer id) {
        Shows findShow = showRepository.findShowsById(id);
        if (findShow != null) {
            findShow.setSoftDelete(true);
            showRepository.save(findShow);
            return true;
        }
        return false;
    }

    @Override
    public List<Shows> searchByDateShow(LocalDate date) {
        return showRepository.searchShowByDateShow(date);
    }

    @Override
    public Page<Shows> searchByDateShowPage(Date date, Pageable pageable) {
        return showRepository.searchShowByDateShowPage(date, pageable);
    }

    @Override
    public List<Shows> searchByStartTime(LocalTime time) {
        return showRepository.searchShowByStartTime(time);
    }

    @Override
    public Page<Shows> searchByStartTimePage(Time time, Pageable pageable) {
        return showRepository.searchShowByStartTimePage(time, pageable);
    }

    @Override
    public Page<Shows> searchByDateAndStartTimePage(Date date, Time time, Pageable pageable) {
        return showRepository.searchShowByDateAndStartTimePage(date, time, pageable);
    }

    @Override
    public List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDateAndStartTime(LocalDate date, LocalTime time) {
        return showRepository.findMovieShowsForDisplayOnDateAndStartTime(date, time);
    }

    @Override
    public List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnDate(LocalDate date) {
        return showRepository.findMovieShowsForDisplayOnDate(date);
    }

    @Override
    public List<ResponseDisplayShowForUser> findMovieShowsForDisplayOnStartTime(LocalTime time) {
        return showRepository.findMovieShowsForDisplayOnStartTime(time);
    }

    @Override
    public List<ResponseDisplayShowForUser> findAllMovieShowsForDisplay() {
        return showRepository.findAllMovieShowsForDisplay();
    }

    @Override
    public List<GroupedMovieShowDto> groupMovieForShow(List<ResponseDisplayShowForUser> list) {
        Map<String, GroupedMovieShowDto> map = new LinkedHashMap<>();
        if (!list.isEmpty()) {
            for (ResponseDisplayShowForUser item : list) {
                String key = item.getIdMovie() + "|" + item.getNameRoom();
                GroupedMovieShowDto movieShowDto = map.get(key);
                if (movieShowDto == null) {
                    movieShowDto = new GroupedMovieShowDto();
                    movieShowDto.setIdMovie(item.getIdMovie());
                    movieShowDto.setTitleMovie(item.getTitleMovie());
                    movieShowDto.setUrl(item.getUrl());
                    movieShowDto.setDuration(item.getDuration());
                    movieShowDto.setDescription(item.getDescription());
                    movieShowDto.setGenre(item.getGenre());
                    movieShowDto.setPrice(item.getPrice());
                    movieShowDto.setNameCinema(item.getNameCinema());
                    movieShowDto.setAddress(item.getAddress());
                    movieShowDto.setSeatCount(item.getSeatCount());
                    movieShowDto.setShowtime(new ArrayList<>());
                    map.put(key, movieShowDto);
                }
                ShowtimeDto showtimeDto = new ShowtimeDto();
                showtimeDto.setIdShow(item.getIdShow());
                showtimeDto.setDateShow(item.getDateShow());
                showtimeDto.setStartTime(item.getStartTime());
                showtimeDto.setEndTime(item.getEndTime());
                showtimeDto.setNameRoom(item.getNameRoom());
                movieShowDto.getShowtime().add(showtimeDto);
            }
            return new ArrayList<>(map.values());
        }
        return null;
    }

    @Override
    public List<RequestBookTicket> detailShowOnBooking(Integer idShow) {
        return showRepository.detailShowOnBooking(idShow);
    }

    @Override
    public ShowBookingInfoDto groupDetailShowOnBooking(List<RequestBookTicket> list) {
        ShowBookingInfoDto showBookingInfoDto = new ShowBookingInfoDto();
        if (!list.isEmpty()) {
            RequestBookTicket itemZero = list.get(0);
            showBookingInfoDto.setIdShow(itemZero.getIdShow());
            showBookingInfoDto.setDateShow(itemZero.getDateShow());
            showBookingInfoDto.setStartTime(itemZero.getStartTime());
            showBookingInfoDto.setEndTime(itemZero.getEndTime());
            MovieInfoDto movieInfoDto = new MovieInfoDto();
            movieInfoDto.setTitleMovie(itemZero.getTitleMovie());
            movieInfoDto.setDescription(itemZero.getDescription());
            movieInfoDto.setDuration(itemZero.getDuration());
            movieInfoDto.setGenreMovie(itemZero.getGenreMovie());
            movieInfoDto.setUrlMovie(itemZero.getUrlMovie());
            movieInfoDto.setPriceMovie(itemZero.getPriceMovie());
            showBookingInfoDto.setMovieInfoDto(movieInfoDto);
            RoomInfoDto roomInfoDto = new RoomInfoDto();
            roomInfoDto.setNameRoom(itemZero.getNameRoom());
            roomInfoDto.setSeatCount(itemZero.getSeatCount());
            showBookingInfoDto.setRoomInfoDto(roomInfoDto);
            CinemaInfoDto cinemaInfoDto = new CinemaInfoDto();
            cinemaInfoDto.setNameCinema(itemZero.getNameCinema());
            cinemaInfoDto.setAddressCinema(itemZero.getAddressCinema());
            showBookingInfoDto.setCinemaInfoDto(cinemaInfoDto);
            List<SeatStatusDto> seatList = new ArrayList<>();
            for (RequestBookTicket item : list) {
                SeatStatusDto seatStatusDto = new SeatStatusDto();
                seatStatusDto.setSeatId(item.getSeatId());
                seatStatusDto.setSeatCode(item.getSeatCode());
                seatStatusDto.setSeatStatus(item.getStatusTicket());
                seatList.add(seatStatusDto);
            }
            showBookingInfoDto.setSeatStatusDto(seatList);
            return showBookingInfoDto;
        }
        return null;
    }

}
