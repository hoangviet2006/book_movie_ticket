package com.example.book_movie_tickets.config;

import com.example.book_movie_tickets.service.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;
    @Autowired
    private UserDetail userDetail;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // lấy authentication
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        http.csrf(csrf->csrf.disable())
                .cors(Customizer.withDefaults()) // sử dụng các cấu hình trong webConfig
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/movie/**").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/cart/**").permitAll()
                        .requestMatchers("/ws-seat/**").permitAll()
                        .requestMatchers("/api/payment/vnpay/**").permitAll()
                        .requestMatchers("/api/user/forgot-password").permitAll()
                        .requestMatchers("/api/user/forgot-password-otp").permitAll()
                        .requestMatchers("/api/user/update-password").permitAll()
//                        .requestMatchers("/api/user/**").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetail).passwordEncoder(passwordEncoderConfig.passwordEncoder());
        // xác thực mật khẩu của người dùng
    }


}
