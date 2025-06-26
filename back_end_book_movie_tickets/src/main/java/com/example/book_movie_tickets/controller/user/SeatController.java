package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.dto.SeatSelectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SeatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // dùng để gửi tin nhắn từ server-> client
    @MessageMapping("/select-seat")  // annotation này giống như post nhưng dùng cho websocket
    public void selectSeat(SeatSelectionDto selectionDto){ // khi client gửi đến /app/select... thì phương thức này được gọi
        System.out.println("Ghế được chọn: " + selectionDto.getSeatId() + ", held = " + selectionDto.isHeld());
        messagingTemplate.convertAndSend("/topic/select-seat-update",selectionDto);
    }
}
