package com.example.book_movie_tickets.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void setMailSender(String email,String otpCode){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // email người nhận
        message.setSubject("Mã OTP xác thực đăng nhập"); // tiêu đề
        message.setText("Mã OTP xác thực của bạn là "+otpCode+" \n Vui lòng không chia sẽ với người khác \n Mã chỉ có hiệu lực 5 phút sau khi tạo tài khoản"); // nội dung
        mailSender.send(message); // gọi phương thức gửi mail
    }
    public void sendTicketByEmail(String toEmail,byte[] pdfData) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Vé xem phim của bạ");
        mimeMessageHelper.setText("Cảm ơn bạn đã đặt vé. Vé xem phim của bạn được đính kèm dưới dạng PDF.", false);
        ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfData,"application/pdf");
        mimeMessageHelper.addAttachment("ve-xem-phim",dataSource);
        mailSender.send(message);
    }

}
