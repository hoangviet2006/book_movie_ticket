package com.example.book_movie_tickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    @Enumerated(EnumType.STRING)
    private Status status=Status.UNPAID;
    public enum Status{
        PAID,
        UNPAID,
        CANCELLED
    }
    @Column(name = "txn_ref")
    private String txnRef;
    @JoinColumn(name = "booking_id")
    @ManyToOne
    private Booking booking;
    @JoinColumn(name = "seat_id")
    @ManyToOne
    private Seat seat;
}
