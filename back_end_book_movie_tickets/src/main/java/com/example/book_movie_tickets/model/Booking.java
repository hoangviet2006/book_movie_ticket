package com.example.book_movie_tickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "total_price")
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status=Status.UNPAID;
    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    @Column(name = "soft_delete")
    private boolean softDelete;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @JoinColumn(name = "show_id")
    @ManyToOne
    private Shows shows;
    @JoinColumn(name = "promotion_id")
    @ManyToOne
    private Promotion promotion;
    public enum Status{
        PAID,
        UNPAID,
    }

}
