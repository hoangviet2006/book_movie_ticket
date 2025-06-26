package com.example.book_movie_tickets.controller.user;

import com.example.book_movie_tickets.dto.CartDto;
import com.example.book_movie_tickets.repository.ICartRepository;
import com.example.book_movie_tickets.service.impl.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private ICartService cartService;
    @GetMapping("")
    public ResponseEntity<?> findCart(HttpServletRequest httpRequest){
        List<CartDto> cartDtoList = cartService.findAllCart(httpRequest);
        if (cartDtoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không có vé trong hệ thống");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartDtoList);
    }
}
