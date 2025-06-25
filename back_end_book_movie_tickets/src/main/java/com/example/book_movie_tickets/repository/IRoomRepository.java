package com.example.book_movie_tickets.repository;

import com.example.book_movie_tickets.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room,Integer> {
    Room findRoomById(int id);

    @Query(value = "select count(*) from room r join cinema c on c.id=r.cinema_id\n" +
                   "where r.soft_delete = false and( r.name=?1 and c.id=?2)",nativeQuery = true)
    int checkRoom(String name,int id);


    @Query(value = "select count(*) from room r join cinema c on c.id=r.cinema_id\n" +
                   "where r.soft_delete = false and r.id!=?1 and(r.name=?2 and c.id=?3)",nativeQuery = true)
    int checkRoomUpdate(int idRoom,String name,int idCinema);

    @Query(value = "select * from room r where r.soft_delete = false",nativeQuery = true)
    List<Room> findAllRoom();

    @Query(value = "select * from room r where r.soft_delete = false",nativeQuery = true,
                    countQuery = "select * from room r where r.soft_delete = false")
    Page<Room> findAllRoomPage(Pageable pageable);

    @Query(value = "select * from room r where r.soft_delete = false and r.name like ?1",nativeQuery = true)
    List<Room> searchRoomByName(String name);

    @Query(value = "select * from room r where r.soft_delete = false and r.name like ?1",nativeQuery = true,
                    countQuery = "select * from room r where r.soft_delete = false and r.name like ?1")
    Page<Room> searchRoomByNamePage(String name,Pageable pageable);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count>= ?1",nativeQuery = true)
    List<Room> searchRoomBySeatNumberStart(Integer seatNumberStart);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count>= ?1",nativeQuery = true,
                    countQuery = "select * from room r where r.soft_delete = false and r.seat_count>= ?1")
    Page<Room> searchRoomBySeatNumberStartPage(Integer seatNumberStart,Pageable pageable);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count<= ?1",nativeQuery = true)
    List<Room> searchRoomBySeatNumberEnd(Integer seatNumberEnd);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count<= ?1",nativeQuery = true,
                    countQuery = "select * from room r where r.soft_delete = false and r.seat_count<= ?1")
    Page<Room> searchRoomBySeatNumberEndPage(Integer seatNumberEnd,Pageable pageable);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count between ?1 and ?2",nativeQuery = true)
    List<Room> searchRoomBySeatNumber(Integer seatNumberStart,Integer seatNumberEnd);

    @Query(value = "select * from room r where r.soft_delete = false and r.seat_count between ?1 and ?2",nativeQuery = true)
    Page<Room> searchRoomBySeatNumberPage(Integer seatNumberStart,Integer seatNumberEnd,Pageable pageable);

//    @Query(value = "select * from room r join cinema c on r.cinema_id=c.id \n" +
//           "where r.soft_delete=false and c.soft_delete=false and c.name like ?1",nativeQuery = true)
//    List<Room> searchRoomByNameCinema(String nameCinema);
//
//    @Query(value = "select r.*,c.name from room r join cinema c on r.cinema_id=c.id \n" +
//                   "                   where r.soft_delete=false and c.soft_delete=false and c.name like ?1",nativeQuery = true,
//                    countQuery = "select r.id,r.name,r.seat_count,c.name  from room r join cinema c on r.cinema_id=c.id \n" +
//                                 "                   where r.soft_delete=false and c.soft_delete=false and c.name like ?1")
//    Page<Room> searchRoomByNameCinemaPage(String nameCinema,Pageable pageable);


    @Query(value = "SELECT * FROM book_movie_tickets.room \n" +
                   "select r.id,r.name,r.seat_count,c.name from room r\n" +
                   "join cinema c on r.cinema_id=c.id\n" +
                   "where r.soft_delete = false and r.name like  ?1 and r.seat_count between ?2 and ?3",nativeQuery = true,
            countQuery = "SELECT * FROM book_movie_tickets.room \n" +
                         "select r.id,r.name,r.seat_count,c.name from room r\n" +
                         "join cinema c on r.cinema_id=c.id\n" +
                         "where r.soft_delete = false and r.name like  ?1 and r.seat_count between ?2 and ?3")
    Page<Room> search(String name,Integer seatNumberStart,Integer seatNumberEnd,Pageable pageable);
}
