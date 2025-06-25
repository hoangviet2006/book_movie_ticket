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
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "seat_count")
    private int seatCount;
    @Column(name = "soft_delete")
    private boolean softDelete=false;
    @JoinColumn(name = "cinema_id")
    @ManyToOne
    private Cinema cinema;
}
