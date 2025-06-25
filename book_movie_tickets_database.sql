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
payment_time datetime,
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
booking_id int,
seat_id int,
foreign key(booking_id) references booking(id),
foreign key(seat_id) references seat(id)
);
create table otp_code (
id int primary key auto_increment,
email VARCHAR(255) NOT NULL,
code VARCHAR(10) NOT NULL,
username varchar(255),
password varchar(255),
created_at datetime,
expired_at datetime
);
insert into movie (title, description, duration, genre, price, poster_url, release_date) values
('Avengers: Endgame', 'Marvel superhero epic', 181, 'Action', 120000, 'https://example.com/avengers.jpg', '2019-04-26'),
('Inception', 'Dreams within dreams', 148, 'Sci-Fi', 100000, 'https://example.com/inception.jpg', '2010-07-16'),
('Interstellar', 'Space and time adventure', 169, 'Sci-Fi', 110000, 'https://example.com/interstellar.jpg', '2014-11-07'),
('Parasite', 'Thrilling Korean drama', 132, 'Drama', 90000, 'https://example.com/parasite.jpg', '2019-05-30'),
('The Dark Knight', 'Batman vs Joker', 152, 'Action', 95000, 'https://example.com/darkknight.jpg', '2008-07-18');
insert into cinema (name, address) values
('CGV Nguyễn Trãi', '123 Nguyễn Trãi, Hà Nội'),
('Galaxy Nguyễn Du', '456 Nguyễn Du, TP.HCM'),
('Lotte Cộng Hòa', '789 Cộng Hòa, TP.HCM');
insert into promotion (code, start_time, end_time) values
('SUMMER2025', '2025-06-01 00:00:00', '2025-06-30 23:59:59'),
('NEWUSER', '2025-01-01 00:00:00', '2025-12-31 23:59:59'),
('WEEKEND50', '2025-06-21 00:00:00', '2025-06-23 23:59:59');
insert into payment (status, payment_method) values
('PAID', 'Credit Card'),
('PENDING', 'Momo'),
('FAILED', 'ZaloPay'),
('PAID', 'Cash');
insert into booking (booking_time, total_price, status, payment_time, user_id, show_id, promotion_id, payment_id) values
('2025-06-20 15:00:00', 240000, 'CONFIRMED', '2025-06-20 15:01:00', 2, 1, 1, 1),
('2025-06-20 16:00:00', 180000, 'PENDING', '2025-06-20 15:01:00', 2, 2, 2, 2),
('2025-06-21 12:00:00', 120000, 'CANCELLED', '2025-06-20 15:01:00', 2, 3, null, 3),
('2025-06-21 13:30:00', 300000, 'CONFIRMED', '2025-06-21 13:31:00', 2, 3, 3, 4),
('2025-06-22 10:00:00', 150000, 'CONFIRMED', '2025-06-22 10:01:00', 2, 2, null, 1),
('2025-06-23 11:30:00', 200000, 'PENDING', '2025-06-20 15:01:00', 2, 1, 1, 2),
('2025-06-24 12:45:00', 175000, 'CONFIRMED', '2025-06-24 12:46:00', 2, 1, 2, 1),
('2025-06-25 14:00:00', 160000, 'CONFIRMED', '2025-06-25 14:01:00', 2, 2, null, 4);
insert into ticket (price, status, booking_id, seat_id) values
(120000, 'PAID', 33, 1),
(120000, 'PAID', 34, 2),
(90000, 'UNPAID', 35, 6),
(90000, 'PAID', 36, 7),
(120000, 'PAID', 37, 11),
(100000, 'PAID', 38, 16),
(100000, 'PAID', 39, 17),
(100000, 'PAID', 40, 18)


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
    
