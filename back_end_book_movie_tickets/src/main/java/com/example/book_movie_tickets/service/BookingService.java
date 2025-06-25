//package com.example.book_movie_tickets.service;
//
//import com.example.book_movie_tickets.config.JwtUtil;
//import com.example.book_movie_tickets.dto.BookingRequest;
//import com.example.book_movie_tickets.model.*;
//import com.example.book_movie_tickets.repository.IBookingRepository;
//import com.example.book_movie_tickets.repository.ISeatRepository;
//import com.example.book_movie_tickets.repository.ITicketRepository;
//import com.example.book_movie_tickets.repository.IUserRepository;
//import com.example.book_movie_tickets.service.impl.BookingServiceInterface;
//import com.example.book_movie_tickets.service.impl.IPromotionRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BookingService implements BookingServiceInterface {
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private IUserRepository userRepository;
//    @Autowired
//    private ShowService showService;
//    @Autowired
//    private IPromotionRepository promotionRepository;
//    @Autowired
//    private IBookingRepository bookingRepository;
//    @Autowired
//    private ITicketRepository ticketRepository;
//    @Autowired
//    private ISeatRepository seatRepository;
//
//
//    @PostConstruct
//    public void init() {
//        System.out.println("BookingService bean has been created!");
//    }
//
//
//    @Override
//    public boolean bookTickets(BookingRequest bookingRequest, HttpServletRequest httpRequest) {
//        String authHeader = httpRequest.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            String username = jwtUtil.getUserNameByToken(token);
//            Optional<User> user = userRepository.findByUsername(username);
//            if (user.isEmpty()) return false;
//            Shows shows = showService.detailShowById(bookingRequest.getIdShow());
//            if (shows == null) return false;
//            Booking booking = new Booking();
//            booking.setUser(user.get());
//            booking.setShows(shows);
//            booking.setPaymentTime(null);
//            booking.setStatus(Booking.Status.UNPAID);
//            booking.setSoftDelete(false);
//            double price = shows.getMovie().getPrice();
//            double totalPrice = price * bookingRequest.getIdSeats().size();
//            if (bookingRequest.getPromotionId() != null) {
//                Optional<Promotion> promotion = promotionRepository.findById(bookingRequest.getPromotionId());
//                if (promotion.isPresent()) {
//                    double discountPercent = promotion.get().getPercent();
//                    totalPrice = totalPrice - (totalPrice * discountPercent / 100);
//                    booking.setPromotion(promotion.get());
//                }
//            }
//            booking.setTotalPrice(totalPrice);
//            bookingRepository.save(booking);
//            List<Integer> ticketIds = new ArrayList<>();
//            for (Integer item : bookingRequest.getIdSeats()) {
//                Optional<Seat> seat = seatRepository.findById(item);
//                if (seat.isPresent()) {
//                    Ticket ticket = new Ticket();
//                    double priceTicket = totalPrice / bookingRequest.getIdSeats().size();
//                    ticket.setPrice(priceTicket);
//                    ticket.setStatus(Ticket.Status.UNPAID);
//                    ticket.setBooking(booking);
//                    ticket.setSeat(seat.get());
//                    Ticket saveTicket = ticketRepository.save(ticket);
//                    ticketIds.add(saveTicket.getId());
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//}
