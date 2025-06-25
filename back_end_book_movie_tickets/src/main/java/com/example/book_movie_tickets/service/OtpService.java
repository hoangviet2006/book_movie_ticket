package com.example.book_movie_tickets.service;

import com.example.book_movie_tickets.model.OtpCode;
import com.example.book_movie_tickets.repository.IOtpRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {
    @Autowired
    private IOtpRepository otpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void generateOtp(String email, String otp, String username, String password) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTimeAt = now.plusMinutes(5);
        otpRepository.deleteByEmail(email);
        OtpCode otpCode = new OtpCode();
        otpCode.setEmail(email);
        otpCode.setUsername(username);
        otpCode.setPassword(passwordEncoder.encode(password));
        otpCode.setCode(otp);
        otpCode.setCreatedAt(now);
        otpCode.setExpiredAt(dateTimeAt);
        otpRepository.save(otpCode);
    }

    public boolean validateOtp(String email, String otp) {
        Optional<OtpCode> otpCode = otpRepository.findByEmail(email);
        if (otpCode.isPresent()) {
            OtpCode getOtp = otpCode.get();
            if (getOtp.getCode().equals(otp) && getOtp.getExpiredAt().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

}
