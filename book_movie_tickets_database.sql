create database book_movie_tickets;
use book_movie_tickets;
create table user(
  id int primary key auto_increment,
  username varchar(255),
  password varchar(255),
  email varchar(255),
  soft_delete boolean default  false,
  role varchar(255)
);
create table movie(
  id int primary key auto_increment,
  title varchar(255),
  description text,
  duration int,
  genre varchar(255),
  price double,
  soft_delete boolean default false,
  poster_url varchar(255),
  release_date date
); 
create table cinema(
  id int primary key auto_increment,
  name varchar(255),
  address varchar(255),
  soft_delete boolean default false
); 
create table room(
   id int primary key auto_increment,
   name varchar(255),
   seat_count int,
   status varchar(255),
   soft_delete boolean default false,
   cinema_id int,
   foreign key (cinema_id) references cinema(id)
);
create table seat(
  id int primary key auto_increment,
  seat_code varchar(255),
  room_id int,
  foreign key(room_id) references room(id)
);
create table shows(
id int primary key auto_increment,
date_show date,
start_time time,
end_time time,
soft_delete boolean default false,
movie_id int,
room_id int,
foreign key(movie_id) references movie(id),
foreign key(room_id) references room(id)
);
create table promotion(
id int primary key auto_increment,
code varchar(255),
percent int,
soft_delete boolean default  false,
start_time datetime,
end_time datetime
);
create table payment(
id int primary key auto_increment,
status varchar(255),
payment_method varchar(255)
);
create table booking(
id int primary key auto_increment,
total_price double,
status varchar(255),
soft_delete boolean default  false,
user_id int,
show_id int,
promotion_id int,
payment_id int,
foreign key(user_id) references user(id),
foreign key(show_id) references shows(id),
foreign key(promotion_id) references promotion(id),
foreign key(payment_id) references payment(id)
);
create table ticket(
id int primary key auto_increment,
price double,
status varchar(255),
book_ticket_time datetime,
booking_id int,
seat_id int,
foreign key(booking_id) references booking(id),
foreign key(seat_id) references seat(id)
);
SHOW COLUMNS FROM ticket WHERE Field = 'status';
create table otp_code (
id int primary key auto_increment,
email VARCHAR(255) NOT NULL,
code VARCHAR(10) NOT NULL,
username varchar(255),
password varchar(255),
created_at datetime,
expired_at datetime
);
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ticket;
TRUNCATE TABLE booking;
TRUNCATE TABLE payment;
TRUNCATE TABLE promotion;
TRUNCATE TABLE shows;
TRUNCATE TABLE seat;
TRUNCATE TABLE room;
TRUNCATE TABLE cinema;
TRUNCATE TABLE user;
TRUNCATE TABLE otp_code;
SET FOREIGN_KEY_CHECKS = 1;

-- select * from seat s join room r on r.id=s.room_id where r.id=1;
-- select COUNT(*) from movie m where m.soft_delete = false and m.title="" and m.release_date=?;
-- select * from movie m where m.soft_delete= false;
-- select * from movie m where m.soft_delete= false and m.genre like "%%";
-- select * from movie m where m.soft_delete= false and (m.genre like "%%" and m.title like "%");
-- select * from cinema c where c.soft_delete = false;
-- select count(*) from cinema c where c.soft_delete = false and c.name=?1 and c.address=?2;
-- select * from cinema c where c.soft_delete = false and c.name like "%%";
-- select * from cinema c where c.soft_delete = false and c.address like "%%";
-- select * from cinema c where c.soft_delete = false and (c.name like "%%" and c.address like "%%");
-- select count(*) from room r join cinema c on c.id=r.cinema_id
-- where r.soft_delete = false and( r.name=?1 and c.id=?2);
-- select * from room r where r.soft_delete = false;
-- select * from room r where r.soft_delete = false and r.name like "%%";
-- select * from room r where r.soft_delete = false and r.seat_count between ? and ?;
-- select * from room r where r.soft_delete = false and r.seat_count>= ?;
-- select * from room r where r.soft_delete = false and r.seat_count<= ?;
-- select * from room r join cinema c on r.cinema_id=c.id 
-- where r.soft_delete=false and c.soft_delete=false and c.name like "%%";

-- select * from shows s 
-- join room r on s.room_id=r.id 
-- join movie m on m.id=s.movie_id  
-- where s.soft_delete = false and s.date_show=?1 and r.id=?2 and(
-- (?3 between s.start_time and s.end_time) or
-- (?4 between s.start_time and s.end_time) or
-- (s.start_time between ?3 and ?4)
-- )

-- select * from shows s where s.soft_delete = false;
-- select * from shows s where s.soft_delete = false and s.date_show = ?;
-- select * from shows s where s.soft_delete = false and s.start_time=?;

-- select * from room r
-- join cinema c on r.cinema_id=c.id
-- where r.soft_delete = false and r.name like "Phòng 5" and c.name like "Việt Hoàng" and r.seat_count between 1 and 15;

-- 	select s.id,
--            m.title as titleMovie,
--            m.poster_url as poster ,
--            m.duration as duration,
--            m.description as description,
--            m.genre as genre,
--            m.price as price,
-- 		   s.date_show as dateShow,
-- 		   s.start_time as startTime,
-- 		   s.end_time as endTime,
-- 		   r.name as nameRoom,
-- 		   r.seat_count as searCount,
-- 		   c.name as nameCinema,
-- 		   c.address as address 
-- 		   from shows s 
-- 	join movie m on m.id=s.movie_id 
-- 	join room r on r.id=s.room_id
-- 	join cinema c on c.id=r.cinema_id where m.id=15 and s.soft_delete = false
-- 	and r.soft_delete = false and m.soft_delete=false
-- 	and c.soft_delete = false;
--     
--     select s.id as idShow,
--            s.date_show as dateShow,
--            s.start_time as startTime,
--            s.end_time as endTime,
--            m.id as idMovie,
--            m.title as titleMovie,
--            m.description as description,
--            m.duration as duration,
--            m.genre as genre,
--            m.price as price,
--            m.poster_url as url,
--            r.name as nameRoom,
--            r.seat_count as seatCount,
--            c.name as nameCinema,
--            c.address as address from shows s 
--     join movie m on m.id=s.movie_id 
--     join room r on r.id=s.room_id 
--     join cinema c on c.id=r.cinema_id
--     where
--     s.soft_delete=false and m.soft_delete=false and r.soft_delete=false and c.soft_delete=false;
--     
--     select sh.id as idShow,
--     sh.date_show as dateShow,
--     sh.start_time as startTime,
--     sh.end_time as endTime,
--     m.title as titleMovie,
--     m.description as description,
--     m.duration as duration,
--     m.genre as genreMovie,
--     m.poster_url as urlMovie,
--     m.price as priceMovie,
--     r.name as nameRoom,
--     r.seat_count as seatCount,
--     s.id as seatId,
--     s.seat_code as seatCode,
--     t.status as statusTicket
--     from shows sh 
--     join movie m on m.id=sh.movie_id 
--     join room r on r.id=sh.room_id 
--     join seat s on r.id=s.room_id 
--     left join ticket t on t.seat_id=s.id 
--     and t.booking_id in (select b.id from booking b where b.show_id=sh.id)
--     where sh.soft_delete=false
--     and m.soft_delete=false 
--     and r.soft_delete=false 
--     and sh.id=14;
                         -- detail show
                       select sh.id as idShow,
                       sh.date_show as dateShow,
                       sh.start_time as startTime,
                       sh.end_time as endTime,
                       m.title as titleMovie,
                       m.description as description,
                       m.duration as duration,
                       m.genre as genreMovie,
                       m.poster_url as urlMovie,
                       m.price as priceMovie,
                       r.name as nameRoom,
                       r.seat_count as seatCount,
                       s.id as seatId,
                       s.seat_code as seatCode,
                       t.status as statusTicket,
                       c.name as nameCinema,
                       c.address addressCinema
				       from shows sh 
                       join movie m on m.id=sh.movie_id
                       join room r on r.id=sh.room_id 
                       join seat s on r.id=s.room_id 
                       left join ticket t on t.seat_id=s.id
                       and t.booking_id in (select b.id from booking b where b.show_id=sh.id)
                       right join cinema c on c.id=r.cinema_id
                       where sh.soft_delete=false
                       and m.soft_delete=false 
					   and r.soft_delete=false
                       and c.soft_delete=false
                       and sh.id=1;
                       -- giỏ hàng
select b.id as idBookig,
      group_concat(distinct t.id) as idTicket,
      group_concat(t.price) as ticketPrice,
       b.status as statusBooking,
       b.total_price as totalPrice,
       MAX(t.book_ticket_time) as bookTicketTime,
       s.date_show as dateShow,
       s.start_time as startTime,
       s.end_time as endTime,
       r.name as nameRoom,
       c.name as nameCinema,
       c.address as addressCinema,
       m.title as titleMovie,
       m.description as descriptionMovie,
       m.duration as durationMovie,
       m.genre as genreMovie,
       m.poster_url as posterUrl,
       group_concat( distinct se.seat_code) as seatCode,
       s.id as idShow,
       group_concat(distinct se.id)as seatId,
       max(t.status) as statusTicket
from booking b 
join ticket t on t.booking_id=b.id
join shows s on s.id=b.show_id
join room r on r.id=s.room_id
join movie m on m.id=s.movie_id
join cinema c on c.id=r.cinema_id
join seat se on se.id=t.seat_id
join user u on u.id=b.user_id
where u.username="hoangviet"
group by b.id 
order by case
when b.status="UNPAID" then 0 
when b.status="PAID" then 1
when b.status="CANCELLED" then 2
else 2
end,
 b.id desc;
