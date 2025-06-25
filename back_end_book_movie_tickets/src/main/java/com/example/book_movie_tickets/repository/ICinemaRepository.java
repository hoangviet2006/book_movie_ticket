package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.Cinema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICinemaRepository extends JpaRepository<Cinema, Integer> {

    @Query(value = "select count(*) from cinema c where c.soft_delete = false and c.name=?1 and c.address=?2",nativeQuery = true)
    int checkCinema(String name,String address);

    @Query(value = "select count(*) from cinema c where c.soft_delete = false and c.name=?1 and c.address=?2 and c.id!=?3",nativeQuery = true)
    int checkCinemaUpdate(String name,String address,int id);

    Cinema findCinemaById(int id);

    @Query(value = "select * from cinema c where c.soft_delete = false", nativeQuery = true)
    List<Cinema> findAllCinema();

    @Query(value = "select * from cinema c where c.soft_delete = false", nativeQuery = true,
            countQuery = "select * from cinema c where c.soft_delete = false")
    Page<Cinema> findAllCinemaPage(Pageable pageable);

    @Query(value = "select * from cinema c where c.soft_delete = false and c.name like ?1",nativeQuery = true)
    List<Cinema> searchCinemaByName(String name);

    @Query(value = "select * from cinema c where c.soft_delete = false and c.name like ?1",nativeQuery = true,
                     countQuery = "select * from cinema c where c.soft_delete = false and c.name like ?1")
    Page<Cinema> searchCinemaByNamePage(String name,Pageable pageable);

    @Query(value = "select * from cinema c where c.soft_delete = false and c.address like ?1",nativeQuery = true)
    List<Cinema> searchCinemaByAddress(String address);

    @Query(value = "select * from cinema c where c.soft_delete = false and c.address like ?1",nativeQuery = true,
                    countQuery = "select * from cinema c where c.soft_delete = false and c.address like ?1")
    Page<Cinema> searchCinemaByAddressPage(String address,Pageable pageable);

    @Query(value = "select * from cinema c where c.soft_delete = false and (c.name like ?1 and c.address like ?2)",nativeQuery = true)
    List<Cinema> searchCinemaByNameAndAddress(String name,String address);

    @Query(value = "select * from cinema c where c.soft_delete = false and (c.name like ?1 and c.address like ?2)",nativeQuery = true,
                    countQuery = "select * from cinema c where c.soft_delete = false and (c.name like ?1 and c.address like ?2)")
    Page<Cinema> searchCinemaByNameAndAddressPage(String name,String address,Pageable pageable);
}
