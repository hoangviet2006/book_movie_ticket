package com.example.book_movie_tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookMovieTicketsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookMovieTicketsApplication.class, args);
    }

}
