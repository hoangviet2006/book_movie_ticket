    package com.example.book_movie_tickets.config;


    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.beans.SimpleTypeConverter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import javax.crypto.SecretKey;
    import java.io.IOException;
    import java.nio.charset.StandardCharsets;
    import java.util.Date;
    import java.util.List;

    @Component
    public class JwtFilter extends OncePerRequestFilter {
        @Autowired
        private JwtUtil jwtUtil;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            System.out.println("Authorization Header: " + header);
            String username = null;
            String role = null;
            String jwt = null;
            if (header != null && header.startsWith("Bearer ")) {
                jwt = header.substring(7);
                if (jwtUtil.validateToken(jwt)) {
                    username = jwtUtil.getUserNameByToken(jwt);
                    role = jwtUtil.getRoleByToken(jwt);
                    System.out.println("Role ----------------------: " + role);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        List<SimpleGrantedAuthority> simpleGrantedAuthorities
                                = List.of(new SimpleGrantedAuthority(role));// lấy role và tạo role cho người dùng

                        UserDetails userDetails = new User(username, "", simpleGrantedAuthorities); // tạo UserDetail cho người dùng hiện tại để spring có thể đọc được
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                ));
                    }
                }
            }
            filterChain.doFilter(request, response);
        }
    }
