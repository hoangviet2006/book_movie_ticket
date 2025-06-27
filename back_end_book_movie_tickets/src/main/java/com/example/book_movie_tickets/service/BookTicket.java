package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.config.JwtUtil;
import com.example.book_movie_tickets.dto.BookingRequest;
import com.example.book_movie_tickets.dto.TicketMinimal;
import com.example.book_movie_tickets.model.*;
import com.example.book_movie_tickets.repository.IBookingRepository;
import com.example.book_movie_tickets.repository.ISeatRepository;
import com.example.book_movie_tickets.repository.ITicketRepository;
import com.example.book_movie_tickets.repository.IUserRepository;
import com.example.book_movie_tickets.service.impl.IBookTicket;
import com.example.book_movie_tickets.service.impl.IPromotionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookTicket implements IBookTicket {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ShowService showService;
    @Autowired
    private IPromotionRepository promotionRepository;
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ISeatRepository seatRepository;

    @Override
    public List<Integer> bookTickets(BookingRequest bookingRequest, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.getUserNameByToken(token);
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) return null;
            Shows shows = showService.detailShowById(bookingRequest.getIdShow());
            if (shows == null) return null;
            Booking booking = new Booking();
            booking.setUser(user.get());
            booking.setShows(shows);
            booking.setStatus(Booking.Status.UNPAID);
            booking.setSoftDelete(false);
            double price = shows.getMovie().getPrice();
            double totalPrice = price * bookingRequest.getIdSeats().size();
            if (bookingRequest.getPromotionId() != null) {
                Optional<Promotion> promotion = promotionRepository.findById(bookingRequest.getPromotionId());
                if (promotion.isPresent()) {
                    double discountPercent = promotion.get().getPercent();
                    totalPrice = totalPrice - (totalPrice * discountPercent / 100);
                    booking.setPromotion(promotion.get());
                }
            }
            booking.setTotalPrice(totalPrice);
            bookingRepository.save(booking);
            List<Integer> ticketIds = new ArrayList<>();
            for (Integer item : bookingRequest.getIdSeats()) {
                Optional<Seat> seat = seatRepository.findById(item);
                if (seat.isPresent()) {
                    ticketRepository.deleteBySeat_IdAndBooking_Shows_IdAndStatus(seat.get().getId(), bookingRequest.getIdShow(), Ticket.Status.CANCELLED);
                    int count = ticketRepository.countTicketBySeat(seat.get().getId(), bookingRequest.getIdShow(), user.get().getId());
                    if (count >= 1) {
                        List<String> seats = ticketRepository.findSeatCodeByTicket(bookingRequest.getIdSeats(), bookingRequest.getIdShow());
                        String seatNames = String.join(", ", seats);
                        System.out.println("Ghế đã bị đặt: " + seatNames);
                        throw new RuntimeException("Ghế: " + seatNames + " đã được đặt bởi người khác. Vui lòng chọn lại.");
                    }
                    Ticket ticket = new Ticket();
                    double priceTicket = totalPrice / bookingRequest.getIdSeats().size();
                    ticket.setPrice(priceTicket);
                    ticket.setStatus(Ticket.Status.UNPAID);
                    ticket.setBooking(booking);
                    ticket.setSeat(seat.get());
                    ticket.setBookTicketTime(LocalDateTime.now());
                    Ticket saveTicket = ticketRepository.save(ticket);
                    ticketIds.add(saveTicket.getId());
                }
            }
            return ticketIds;
        }
        return null;
    }

    @Scheduled(fixedRate = 60000) // tương đương 1 phút
    public void cancelExpiredTickets() {
        List<TicketMinimal> ticketMinimals = ticketRepository.findTicketOnClose();

        if (!ticketMinimals.isEmpty()) {
            for (TicketMinimal t : ticketMinimals) {
                System.out.println(">>> [DEBUG] Kiểm tra vé ID: " + t.getId() + ", thời gian đặt: " + t.getBookTicketTime());
                if (t.getBookTicketTime() != null && Duration.between(t.getBookTicketTime(), LocalDateTime.now()).toMinutes() > 15) {
                    Optional<Ticket> ticket = ticketRepository.findById(t.getId());
                    if (ticket.isPresent()) {
                        ticket.get().setStatus(Ticket.Status.CANCELLED);
                        ticketRepository.save(ticket.get());
                        System.out.println(">>> [DEBUG] Vé ID " + t.getId() + " đã được hủy do hết hạn.");
                    }
                }
            }
        }
    }
}

