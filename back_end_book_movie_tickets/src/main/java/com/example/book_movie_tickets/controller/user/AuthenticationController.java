package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.config.JwtUtil;
import com.example.book_movie_tickets.dto.JwtDto;
import com.example.book_movie_tickets.dto.JwtResponse;
import com.example.book_movie_tickets.dto.OtpUtil;
import com.example.book_movie_tickets.model.OtpCode;
import com.example.book_movie_tickets.model.User;
import com.example.book_movie_tickets.repository.IOtpRepository;
import com.example.book_movie_tickets.repository.IUserRepository;
import com.example.book_movie_tickets.service.AuthenticationService;
import com.example.book_movie_tickets.service.EmailService;
import com.example.book_movie_tickets.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOtpRepository otpRepository;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Tên đăng nhập đã tồn tại");
            }
            if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email đã được sử dụng");
            }
            String otp = OtpUtil.generateOtp();
            otpService.generateOtp(user.getEmail(), otp, user.getUsername(), user.getPassword());
            emailService.setMailSender(user.getEmail(), otp);
            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("message", "OTP đã được tạo, vui lòng kiểm tra email để xác thực tài khoản");
            System.out.println("Email đăng ký" + user.getEmail() + "OTP : " + otp);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("verify-otp")
    @Transactional
    public ResponseEntity<?> veryFyOtp(@RequestBody OtpCode otpCode) {
        boolean check = otpService.validateOtp(otpCode.getEmail(), otpCode.getCode());
        if (check) {
            Optional<OtpCode> storedOtp = otpRepository.findByEmail(otpCode.getEmail());
            User user = authenticationService.register(
                    storedOtp.get().getUsername(), storedOtp.get().getEmail(), storedOtp.get().getPassword());
            otpRepository.deleteByEmail(user.getEmail());
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new JwtDto(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
    }

    @PostMapping("verify-otp-confirm")
    @Transactional
    public ResponseEntity<?> veryFyOtpConfirm(@RequestBody String email) {
        try {
            email = email.replace("\"", "").trim();
            Optional<OtpCode> otpCode = otpRepository.findByEmail(email);
            if (otpCode.isPresent()) {
                otpRepository.deleteByCode(otpCode.get().getCode());
                String otp = OtpUtil.generateOtp();
                otpService.generateOtp(otpCode.get().getEmail(), otp, otpCode.get().getUsername(), otpCode.get().getPassword());
                emailService.setMailSender(otpCode.get().getEmail(), otp);
                Map<String, String> response = new HashMap<>();
                response.put("email", otpCode.get().getEmail());
                response.put("message", "OTP đã được tạo lại, vui lòng kiểm tra email để xác thực tài khoản");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String token = authenticationService.login(user.getUsername(), user.getPassword());
            JwtDto jwtDto = new JwtDto(token);
            String email = jwtUtil.getEmailByToken(token);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setEmail(email);
            jwtResponse.setJwtDto(jwtDto);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-username")
    public ResponseEntity<?> getUsername( HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.getUserNameByToken(token);
            return ResponseEntity.ok(username);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không lấy được thông tin người dùng");
    }
    @GetMapping("/get-email")
    public ResponseEntity<?> getEmail( HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.getEmailByToken(token);
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không lấy được thông tin người dùng");
    }
    @GetMapping("/get-role")
    public ResponseEntity<?> getRole( HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.getRoleByToken(token);
            return ResponseEntity.ok(username);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không lấy được role");

    }


}
