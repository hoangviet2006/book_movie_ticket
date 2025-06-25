package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.Ticket;
import jdk.dynalink.linker.LinkerServices;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByTxnRef(String txnRef);

}
