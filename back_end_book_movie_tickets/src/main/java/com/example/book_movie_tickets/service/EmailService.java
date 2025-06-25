package com.example.book_movie_tickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void setMailSender(String email,String otpCode){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // email người nhận
        message.setSubject("Mã OTP xác thực đăng nhập"); // tiêu đề
        message.setText("Mã OTP xác thực của bạn là "+otpCode+" \n Vui lòng không chia sẽ với người khác"); // nội dung
        mailSender.send(message); // gọi phương thức gửi mail
    }

}
