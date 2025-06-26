package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.config.JwtUtil;
import com.example.book_movie_tickets.dto.BookingRequest;
import com.example.book_movie_tickets.dto.OtpUtil;
import com.example.book_movie_tickets.dto.RequestPassword;
import com.example.book_movie_tickets.model.OtpCode;
import com.example.book_movie_tickets.model.User;
import com.example.book_movie_tickets.repository.*;
import com.example.book_movie_tickets.service.BookTicket;
import com.example.book_movie_tickets.service.EmailService;
import com.example.book_movie_tickets.service.OtpService;
import com.example.book_movie_tickets.service.ShowService;
import com.example.book_movie_tickets.service.impl.BookingServiceInterface;
import com.example.book_movie_tickets.service.impl.IPromotionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IOtpRepository otpRepository;
    @Autowired
    private BookTicket bookTickets;





    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ShowService showService;
    @Autowired
    private IPromotionRepository promotionRepository;
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ISeatRepository seatRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        System.out.println(bookTickets+"----------------------------------------------------");
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            String otp = OtpUtil.generateOtp();
            otpService.generateOtp(user.get().getEmail(), otp, user.get().getUsername(), user.get().getPassword());
            emailService.setMailSender(user.get().getEmail(), otp);
            return ResponseEntity.ok("OTP đã được gửi đến email của bạn");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy người dùng với email này");
    }

    @PostMapping("/forgot-password-otp")
    public ResponseEntity<?> verifyForgotPassword(@RequestBody OtpCode otpCode) {
        System.out.println(bookTickets+"----------------------------------------------------");
        boolean check = otpService.validateOtp(otpCode.getEmail(), otpCode.getCode());
        if (check) {
            return ResponseEntity.ok("OTP hợp lệ");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("OTP không đúng hoặc đã hết thời gian");
    }

    @PostMapping("/update-password")
    @Transactional
    public ResponseEntity<?> updatePassword(@RequestBody OtpCode otpCode) {

        System.out.println(bookTickets+"----------------------------------------------------");
        Optional<OtpCode> optionalOtpCode = otpRepository.findByEmail(otpCode.getEmail());
        if (optionalOtpCode.isPresent() && otpService.validateOtp(otpCode.getEmail(), otpCode.getCode())) {
            Optional<User> user = userRepository.findUserByEmail(otpCode.getEmail());
            if (user.isPresent()) {
                user.get().setPassword(passwordEncoder.encode(otpCode.getPassword()));
                userRepository.save(user.get());
                otpRepository.deleteByEmail(user.get().getEmail());
                return ResponseEntity.ok("Cập nhật mật khẩu thành công");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật mật khẩu");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody RequestPassword requestPassword) {
        Optional<User> user = userRepository.findByUsername(requestPassword.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(requestPassword.getPassword(), user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(requestPassword.getNewPassword()));
                userRepository.save(user.get());
                return ResponseEntity.ok("Đổi mật khẩu thành công");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật mật khẩu");
    }

   @PostMapping("/booking")
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest bookingRequest,HttpServletRequest httpServletRequest){
       try {
           List<Integer> result = bookTickets.bookTickets(bookingRequest, httpServletRequest);
           if (!result.isEmpty()) {
               return ResponseEntity.ok(result);
           }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đặt vé thất bại");
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(e.getMessage());
       }
   }

}
